java -Dwebdriver.chrome.driver="browserBinaries/chromedriver.exe" -Dwebdriver.gecko.driver="browserBinaries/geckodriver.exe" -jar selenium-server-standalone-3.8.1.jar -role node -hub http://mrunal-laptop:4444/grid/register -maxSession 10 -capabilities "browserName=firefox, maxInstances=10" -capabilities "browserName=chrome, maxInstances=10"