import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        create_pocketbook_files("files/example_notes_old.txt");
    }

    public static void create_pocketbook_files(String oldFormatFile) {

        // read and decode the old file
        List<String> lines = FileReader.readOldFormat("files/example_notes_old.txt");
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
                if((bc.bookAuthor + "_" + bc.bookTitle) == bookTitle)
                {
                    booksComments.add(bc);
                }
            }
            // now the list contains all comments of the book. Therefore, we can create the output file
            // TODO
        }

    }
}