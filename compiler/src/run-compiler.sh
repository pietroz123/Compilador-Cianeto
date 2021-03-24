echo -e "find -name "*.java" > sources.txt"
find -name "*.java" > sources.txt

echo -e "\njavac @sources.txt -d ../bin"
javac @sources.txt -d ../bin

echo -e "\ncd ../bin"
cd ../bin

echo -e "\njava comp/Comp ../../student-made-tests"
java comp/Comp ../../student-made-tests

echo -e "\ncd ../src/"
cd ../src/