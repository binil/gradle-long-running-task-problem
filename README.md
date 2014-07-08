Test project demonstrating problems in executing long-running tasks 
with Gradle. The rootprj contains two sub-projects - subprj1 and subprj2.
subprj2 depends on subprj1. Both sub-projects have a task - runMain - to
execute a long-running java program.

To reproduce the problem:

1. Open a console window and run:

    $ cd rootprj/subprj1
    $ gradle clean runMain
    :subprj1:clean UP-TO-DATE
    :subprj1:compileJava
    :subprj1:processResources UP-TO-DATE
    :subprj1:classes
    :subprj1:runMain
    Tue Jul 08 15:06:51 PDT 2014: time now is 1404857211634
    Tue Jul 08 15:06:53 PDT 2014: time now is 1404857213650
    Tue Jul 08 15:06:55 PDT 2014: time now is 1404857215651
    ...

2. In another console window, run:

    $ cd rootprj/subprj2
    $ gradle clean runMain
    :subprj2:clean
    :subprj1:compileJava UP-TO-DATE
    :subprj1:processResources UP-TO-DATE
    :subprj1:classes UP-TO-DATE
    :subprj1:jar UP-TO-DATE
    :subprj2:compileJava
    :subprj2:processResources UP-TO-DATE
    :subprj2:classes
    :subprj2:runMain
    Tue Jul 08 15:08:03 PDT 2014: time now is 1404857283642
    Tue Jul 08 15:08:05 PDT 2014: time now is 1404857285659
    ...

3. Kill the subprj2 build with Ctrl+C.

4. Edit rootprj/subprj1/build.gradle file by adding an empty new line.

5. Run subprj2 task again.

    $ gradle clean runMain

    FAILURE: Build failed with an exception.

    * What went wrong:
    A problem occurred configuring project ':subprj1'.
    > Could not open buildscript class cache for build file '/Users/bthomas/play/gradle-bug/rootprj/subprj1/build.gradle' (/Users/bthomas/.gradle/caches/1.11/scripts/build_1f6069qdrrgscp5mnl8g0op12c/ProjectScript/buildscript).
       > Timeout waiting to lock buildscript class cache for build file '/Users/bthomas/play/gradle-bug/rootprj/subprj1/build.gradle' (/Users/bthomas/.gradle/caches/1.11/scripts/build_1f6069qdrrgscp5mnl8g0op12c/ProjectScript/buildscript). It is currently in use by another Gradle instance.
         Owner PID: unknown
         Our PID: 76798
         Owner Operation: unknown
         Our operation: Initialize cache
         Lock file: /Users/bthomas/.gradle/caches/1.11/scripts/build_1f6069qdrrgscp5mnl8g0op12c/ProjectScript/buildscript/cache.properties.lock

    * Try:
    Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output.

    BUILD FAILED

    Total time: 1 mins 3.108 secs
    $

-- 
Binil Thomas
binil.thomas@gmail.com

