import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if(args.length != 1)
        {
            System.out.println("Please provide one (1) command line argument conatining the path to the old notes file.");
            return;
        }
        create_pocketbook_files(args[0]);
    }

    public static void create_pocketbook_files(String oldFormatFile) {

        // read and decode the old file
        List<String> lines = FileReader.readFile(oldFormatFile);
        List<List<String>> commentStrings = FileReader.splitCommentsOldFormat(lines);
        List<BookComment> bookComments = new ArrayList<>();
        for(List<String> commentString : commentStrings)
        {
            bookComments.add(BookComment.createFromOldFormat(commentString));
        }

        // create comment lists on a per-book-basis
        List<String> bookTitles = new ArrayList<>();
        for(BookComment bc : bookComments)
        {
            String author_title = bc.bookAuthor + "_" + bc.bookTitle;
            if(!bookTitles.contains(author_title))
            {
                bookTitles.add(author_title);
            }
        }
        for(String bookTitle : bookTitles)
        {
            List<BookComment> booksComments = new ArrayList<>();
            for(BookComment bc : bookComments)
            {
                if((bc.bookAuthor + "_" + bc.bookTitle).equals(bookTitle))
                {
                    booksComments.add(bc);
                }
            }
            // now the list contains all comments of the book. Therefore, we can create the output file
            cFileWriter.writeNewFormat(booksComments);
            // TODO: indendation and formatting in resulting file not yet ideal
        }

    }
}