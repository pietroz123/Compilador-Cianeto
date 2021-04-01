if [ $# -eq 0 ]; then
    echo 'Necessário executar com o student ou code'
    exit 0
fi

echo -e "find -name "*.java" > sources.txt"
find -name "*.java" > sources.txt

echo -e "\njavac @sources.txt -d ../bin"
javac @sources.txt -d ../bin

echo -e "\ncd ../bin"
cd ../bin

if [ $1 = "student" ]; then
    echo -e "\njava comp/Comp ../../student-made-tests"
    java comp/Comp ../../student-made-tests

elif [ $1 = "code" ]; then
    echo -e "\njava comp/Comp ../../code-generation-tests"
    java comp/Comp ../../code-generation-tests

fi

echo -e "\ncd ../src/"
cd ../src/