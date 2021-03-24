JAVA_COMP=javac

all:
	find -name "*.java" > sources.txt
	javac @sources.txt -d ../bin
	cd ../bin
	java comp/Comp ../../student-made-tests

clean:
	$(RM) *.class
