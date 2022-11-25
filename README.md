# E-Reader note converter

Simple program to convert ebook-notes from the Tolino-format (i.e. single .txt files) to the picketbook format (i.e. multiple .html files).

#### Usage:

The program is a simple Java application. Either compile yourself, or use the pre-compiled .jar provided under [releases](https://github.com/DavidBaldsiefen/ereader-note-converter/releases).
The .jar can be executed with java: ```java -jar executable.jar "/path/to/input.txt"```

Please note that the output files may require the .html ```book_hash```-tag to be filled in line 111.
