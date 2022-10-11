# screenshot
Java selenium project that utilizes chrome driver with only purpose to take screenshots from web-sites


# Setup


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

# Usage




