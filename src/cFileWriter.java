import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class cFileWriter {

    public static void writeNewFormat(List<BookComment> bookComments)
    {
        if(bookComments.size() == 0)
        {
            System.out.println("cFileWriter got empty list!");
            return;
        }
        // get title & author
        String title = bookComments.get(0).bookTitle;
        String author = bookComments.get(0).bookAuthor;

        // get header
        List<String> header = FileReader.readFile("files/pocketbook_header.html");

        // create output lines
        List<String> outLines = new ArrayList<>();
        outLines.add("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\">");
        outLines.addAll(header);
        outLines.add("\t<body>");
        outLines.add("\t\t<div class=\"bookmark bm-color-note\">");
        outLines.add("\t\t\t<h1>" + title + "</h1>");
        outLines.add("\t\t</div");
        outLines.add("\t\t<div class=\"bookmark bm-color-note\">");
        outLines.add("\t\t\t<span>" + author + "</span>");
        outLines.add("\t\t</div>");
        for(BookComment bc : bookComments)
        {
            List<String> newFormatComment = bc.toNewFormat();
            newFormatComment.replaceAll(e -> ("\t\t" + e));
            outLines.addAll(newFormatComment);
        }
        outLines.add("\t</body");
        outLines.add("</html>");

        // now write to file
        writeToFile(outLines, author + "_" + title + ".html");
    }

    private static void writeToFile(List<String> lines, String filename)
    {
        try {
            File outFile = new File(filename);
            if (!outFile.createNewFile()) {
                System.out.println("Could not create file " + filename);
                return;
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while creating " + filename);
            ex.printStackTrace();
            return;
        }
        // if we are here, we can write
        try{
            java.io.FileWriter writer = new java.io.FileWriter(filename);
            for(String line : lines)
            {
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("An error occurred while writing to " + filename);
            ex.printStackTrace();
            return;
        }
        System.out.println("Finished writing to " + filename);

    }
}
