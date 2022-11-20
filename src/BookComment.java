import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum CommentType {
    NOTE,
    MARKING,
    BOOKMARK
}

public class BookComment {
    public String bookTitle;
    public String bookAuthor;
    public String quote;
    public String comment;
    public String pages;
    public Date date = new Date();
    public CommentType type;
    boolean valid;

    private BookComment()
    {
        valid = false;
    }
    public static BookComment createFromOldFormat(List<String> lines)
    {
        BookComment bc = new BookComment();
        // we assume correct 3-4-line format
        if(lines.size() < 3 || lines.size() > 4)
        {
            System.out.println("Could not identify comment");
            return bc;
        }
        bc.valid = true;
        // first line contains title and author
        if(lines.get(0).indexOf('(') == -1)
        {
            bc.valid = false;
        }

        bc.bookTitle = lines.get(0).substring(0, lines.get(0).indexOf('(')-1);
        bc.bookAuthor = lines.get(0).substring(lines.get(0).indexOf('(') + 1, lines.get(0).length()-1);

        // second line contains pagenumber, type and quote or comment (at least 4 words)
        String[] words = lines.get(1).split("\\s");
        if(words.length < 4)
            bc.valid = false;


        if(words[0].contains("Notiz"))
        {
            bc.type = CommentType.NOTE;
            if(lines.get(1).indexOf(':') == -1)
            {
                bc.valid = false;
            }
            bc.comment = lines.get(1).substring(lines.get(1).indexOf(':') + 2);

            // note means quote follows on the next line
            bc.quote = lines.get(2).substring(1, lines.get(2).length()-1);
        }
        else
        {
            if(words[0].contains("Lesezeichen"))
            {
                bc.type = CommentType.BOOKMARK;
            }
            else
            {
                bc.type = CommentType.MARKING;
            }

            if(lines.get(1).indexOf('"') == -1)
            {
                bc.valid = false;
            }
            bc.quote = lines.get(1).substring(lines.get(1).indexOf('"') + 1, lines.get(1).length()-1);
        }

        bc.pages = lines.get(1).substring(lines.get(1).indexOf("Seite") + 6, lines.get(1).indexOf(':'));

        // date is on the last line. The '|' separates date and time. it is preceded by "Hinzugefügt am" or "Geändert am"
        String lastLine = lines.get(lines.size() -1);
        String dayStr = lastLine.substring(lastLine.indexOf('|') - 11, lastLine.indexOf('|') - 1);
        String timeStr = lastLine.substring(lastLine.indexOf('|') + 2);
        String dayTime = dayStr + "-" + timeStr;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy-HH:mm");
        try{
            bc.date = formatter.parse(dayTime);
        } catch(ParseException ex)
        {
            System.out.println("Could not parse date \"" + dayTime + "\"");
        }

        return bc;
    }
    public String toString()
    {
        if(valid)
        {
        String str = "Book: " + bookTitle + "\n";
        str += "Author: " + bookAuthor + "\n";
        str += "Type: " + (type == CommentType.MARKING ? "Markierung" : "Notiz") + "\n";
        str += "Quote: " + quote + "\n";
        str += "Comment: " + comment + "\n";
        str += "Page: " + pages + "\n";
        str += "Date: " + date.toString() + "\n";
        return str;
        }
        else
        {
            return "Invalid BookComment";
        }
    }


    /*
    Example new format:
    <div id="E6B1A8B9-7BD1-52EC-95D9-922BD8AA0954" class="bookmark bm-color-note">
      <p class="bm-page">169</p>
      <div class="bm-text">
        <p>Bookmark</p>
      </div>
    </div>

    <div id="D63173FB-66A5-5840-8A0D-D208F7E9BC5E" class="bookmark bm-color-cian">
      <p class="bm-page">170</p>
      <div class="bm-text">
        <p>For it is not private ownership, but private ownership divorced from work</p>
      </div>
      <div class="bm-note">
        <p>jeder nur ein Haus</p>
      </div>
    </div>
     */
    public List<String> toNewFormat()
    {
        ArrayList<String> newFormat = new ArrayList<>();
        if(!valid)
            return newFormat;

        // no clue whats going on with these IDs, so I will just create them randomly (seems like HEX)
        newFormat.add("<div id=\"" + "randomstr" + "\" class=\"bookmark bm-color-cian\">");
        newFormat.add("\t<p class=\"bm-page\">" + pages + "</p>"); // TODO: handle multiple pages, i.e. 120-121
        newFormat.add("\t<div class=\"bm-text\">");
        newFormat.add("\t\t<p>" + quote + "</p>");
        newFormat.add("\t</div>");
        if(type == CommentType.NOTE)
        {
            newFormat.add("\t<div class=\"bm-note\">");
            newFormat.add("\t\t<p>" + comment + "</p>");
            newFormat.add("\t</div>");
        }
        newFormat.add("</div>");

        return newFormat;
    }

}
