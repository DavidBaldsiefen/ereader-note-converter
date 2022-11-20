import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        List<String> lines = FileReader.readOldFormat("files/example_notes_old.txt");
        List<List<String>> commentStrings = FileReader.splitCommentsOldFormat(lines);
        for(int i = 0; i < commentStrings.size(); i++)
        {
            BookComment bc = BookComment.createFromOldFormat(commentStrings.get(i));
            System.out.print(bc.toString());
            System.out.println("------");
        }
        for(int i = 0; i < commentStrings.size(); i++)
        {
            BookComment bc = BookComment.createFromOldFormat(commentStrings.get(i));
            List<String> commentsHTML = bc.toNewFormat();
            for(int j = 0; j < commentsHTML.size(); j++)
            {
                System.out.println(commentsHTML.get(j));
            }
        }
    }



}