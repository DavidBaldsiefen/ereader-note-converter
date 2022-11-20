import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class FileReader {
    public static List<String> readOldFormat(String filename)
    {
        File file;
        Scanner scanner;
        try {
        file = new File(filename);
        scanner = new Scanner(file);;
        } catch (FileNotFoundException ex)
        {
            System.out.println("File \"" + filename + "\" could not be opened!");
            return new ArrayList<>();
        }

        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        System.out.println("Read " +lines.size() + " lines from \"" + filename + "\"");
        scanner.close();
        return lines;
    }

    // Split multiple lines up into sections representing files
    /*
    Old Format:
    1: BookTitle (Author)
    2: CommentType auf Seite Page: "quote"
    3: Hinzugefügt am Date | Time

    Individual comments are seperated by 2 empty lines which surround "-----------------------------------"
    Comments are 3-4 lines
     */
    public static List<List<String>> splitCommentsOldFormat(List<String> lines)
    {
        int lastSeparator = -2;
        List<List<String>> comments = new ArrayList<>();
        for(int i = 0; i < lines.size(); i++)
        {
            if(i >= 4 && lines.get(i).equals("-----------------------------------"))
            {
                int numLines = i - lastSeparator - 3;
                List<String> singleComment = new ArrayList<>();
                for(int j = 0; j < numLines; j++)
                {
                    singleComment.add(lines.get(lastSeparator + 2 + j));
                }

                comments.add(singleComment);
                lastSeparator = i;
            }
        }
        return comments;
    }
}
