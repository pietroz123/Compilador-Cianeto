### Como executar

- Na pasta /compiler/src, executar:

`find -name "*.java" > sources.txt && javac @sources.txt -d ../bin && cd ../bin && java comp/Comp ../../student-made-tests && cd ../src/`