# fbwisher
Project to wish a happy birthday to all facebook friends

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
./gradlew installDist #Create the executable
build/install/fbwisher/bin/fbwisher #You can copy this to preferred location
```

## Setup cronjob
#### Open crontab editor by running
```
crontab -l #List all the crontabs
env EDITOR=nano crontab -e #Open crontab for editing
```

#### Paste the following in the crontab editor
```
#First  : minute (0-59)
#Second : hour (0-23)
#Third  : day of the month (1-31)
#Fourth : month (1-12)
#Fifth  : day of the week (Sunday = 0; Saturday = 6)

#Run every hour at 1 minute
01 * * * * bash -l ~/fbwisher/run.sh > /tmp/fbwisher_log.txt 2>&1
```
