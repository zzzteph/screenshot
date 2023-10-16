# screenshot

Java selenium project that utilizes chrome driver with the only purpose of taking screenshots from websites. It can be easily integrated for different automation or used as scaffolding to make more "clever" bots that emulate user interactions with websites.

# Setup


## Docker Container (Pref)


- Install Docker
- Pull the Docker image by running ```docker pull zzzteph/screenshot```
- Run ```docker run -v /tmp:/tmp zzzteph/screenshot -u "http://google.com" -s "/tmp/google.png"```



## Manual

0. Install JDK and additional software

```
sudo apt-get install -y curl xvfb libxi6 libgconf-2-4 default-jdk maven```
```



1. Setup latest Google Chrome.
```
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo apt install ./google-chrome-stable_current_amd64.deb
```

2. Download and install ChromeDriver.
```
wget https://chromedriver.storage.googleapis.com/2.41/chromedriver_linux64.zip
unzip chromedriver_linux64.zip

sudo mv chromedriver /usr/bin/chromedriver
sudo chown root:root /usr/bin/chromedriver
sudo chmod +x /usr/bin/chromedriver
```

3. Preapare package 


```
git clone https://github.com/zzzteph/screenshot
cd screenshot
mvn package
```

You will be able to find **JAR** (screenshot-1.0.jar) file in target folder.

```
cd target
java -jar screenshot-1.0.jar

```


# Usage


```
java -jar screenshot-1.0.jar -u "https://google.com" -s google.com.png
```

The application will visit **https://google.com**, wait 5 seconds for all Javascript to load and execute, and then take a screenshot and store it at **google.com.png**.
You will be able to find **google.com.png** image in the folder. You can specify the path where you want to store the image.


<p align="center">
  <img src="https://github.com/zzzteph/screenshot/blob/main/google.com.png?raw=true">
</p>





