# HTMLProcessor
Processes HTML files server side so that you can include html files inside other html files, or inject dynamically generated html into template pages.

### How to use it

##### Getting started
If you compile and run it without any changes, it starts a small server to serve webpages. At its current state, the server only serves the index.html from the pages directory, no matter what page you requested.

##### Editing/creating HTML files
Variables are in the form `{variablename}`. The variable will be replaced with a value if it matches any variables passed in when the page is processed. You can also include other files using relative paths such as `{content.html}`, `{path/to/my/file.txt}`, or `{../myFile.html}`. Note that only .html files will processed for variables. Other file types will be statically included without any further proceesing.

Note 2: there is currently no protection against circular references between html files. Circular references will cause an infinite loop and eventually the program to crash.

##### Adding variables
To add more variables that a page can use when being processed, pass in your variables as key, value pairs where the key is the variable's name and the value is the varaible's value when `getPage` or `getPageWithLocalRefs` is called.
