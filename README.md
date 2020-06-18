# fbwisher

## Install
#### Download and setup the project
```
cd ~/
git clone https://github.com/ggspark/fbwisher
cd fbwisher
git checkout master
git pull origin master
```
#### Run the project
```
cd ~/fbwisher
./gradlew installDist
build/install/fbwisher/bin/fbwisher
```


## Setup cronjob
#### Open crontab editor by running
```
crontab -l
env EDITOR=nano crontab -e
```

#### Paste the following in the crontab editor
```
#First  : minute (0-59)
#Second : hour (0-23)
#Third  : day of the month (1-31)
#Fourth : month (1-12)
#Fifth  : day of the week (Sunday = 0; Saturday = 6)

#Daily
01 00 * * * cd ~/; bash -l ~/fbwisher/build/install/fbwisher/bin/fbwisher > /tmp/fbwisher_log.txt 2>&1
```
