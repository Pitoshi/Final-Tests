Как запустить тесты

1. Запустить командную строку и в ней перейти в директорию куда планируется загрузить тесты

2. В командной строке ввести команду:
git clone https://github.com/zheka0505/finalCertification.git

3. Перейти в директорию finalCertification командой:
cd finalCertification

4. Запустить тесты командой:
mvn clean test

5. Запустить отчет командой:
allure serve