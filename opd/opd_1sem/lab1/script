#!/usr/bin/bash

chmod -R 777 lab0
rm -r lab0

#Вариант 1705

#1

mkdir lab0
cd lab0
echo -e 'satk 10 sdef=8 spd=10' > arcanine4
mkdir cinccino6
echo -e 'satk-8 sdef=8\nspd=14' > cinccino6/electrode
mkdir cinccino6/sudowoodo
mkdir cinccino6/chingling
mkdir duskull6
echo -e 'weigth=67.2 height=43.0 atk-10\ndef=6' > duskull6/simisage
mkdir duskull6/roggenrola
mkdir duskull6/marshtomp
mkdir hariyama3
echo -e 'Способности Clamp Iron Defense Water Gun Whirlpool\nShell Smash' > hariyama3/clamperl
mkdir hariyama3/dusclops
echo -e 'Способности Dark Art Illusion\nPrankster' > hariyama3/zorua 
mkdir hariyama3/drowzee
echo -e 'satk 4 sdef=4 spd=5' > skitty8
echo -e 'Возможности\nOverland 8 Surface 6 Jump=4 Power 3 Intelligence 4 Tracker=0' > watchog0

#2

chmod 624 arcanine4
chmod 363 cinccino6
chmod 622 cinccino6/electrode
chmod 750 cinccino6/sudowoodo
chmod 311 cinccino6/chingling
chmod 755 duskull6
chmod 444 duskull6/simisage
chmod 512 duskull6/roggenrola
chmod 512 duskull6/marshtomp
chmod 330 hariyama3
chmod 540 hariyama3/clamperl
chmod 576 hariyama3/dusclops
chmod 640 hariyama3/zorua
chmod 573 hariyama3/drowzee
chmod 044 skitty8
chmod 444 watchog0

#3
#3.1
chmod 777 skitty8
cp skitty8 cinccino6/sudowoodo

	#[s466380@helios ~/lab0]$ ls -p cinccino6/sudowoodo
	#skitty8

#3.2
ln -s ../watchog0 hariyama3/clamperlwatchog

	#[s466380@helios ~/lab0]$ cat hariyama3/clamperlwatchog 
	#Возможности
	#Overland 8 Surface 6 Jump=4 Power 3 Intelligence 4 Tracker=0

#3.3
ln skitty8 hariyama3/clamperlskitty

	#[s466380@helios ~/lab0]$ ls -p hariyama3/clamperlskitty
	#hariyama3/clamperlskitty

#3.4
chmod 777 hariyama3
chmod 777 hariyama3/drowzee
cp -r hariyama3 hariyama3/drowzee

	#[s466380@helios ~/lab0]$ cd hariyama3/drowzee/hariyama3 
	#[s466380@helios ~/lab0/hariyama3/drowzee/hariyama3]$ ls 
	#clamperl	clamperlskitty	drowzee		dusclops	zorua

#3.5
ln -s ./cinccino6 Copy_28

	#[s466380@helios ~/lab0]$ ls -la | grep ^l
	#lrwxr-xr-x  1 s466380 studs 12 10 окт.  19:42 cinccino6 -> ../cinccino6

#3.6
chmod 777 skitty8
cp skitty8 hariyama3/clamperlskitty 

	#[s466380@helios ~/lab0]$ cat hariyama3/clamperlskitty1
	#satk 4 sdef=4 spd=5

#3.7
cat hariyama3/clamperl duskull6/simisage > arcanine4_99

	#[s466380@helios ~/lab0]$ cat hariyama3/clamperl
	#Способности Clamp Iron Defense Water Gun Whirlpool
	#Shell Smash
	#[s466380@helios ~/lab0]$ cat duskull6/simisage
	#weigth=67.2 height=43.0 atk-10
	#def=6
	#[s466380@helios ~/lab0]$ cat arcanine4_99
	#Способности Clamp Iron Defense Water Gun Whirlpool
	#Shell Smash
	#weigth=67.2 height=43.0 atk-10
	#def=6

#4

#4.1
wc -l cinccino6/electrode duskull6/simisage hariyama3/clamperl 2>/dev/null | sort | grep -v total

	#2 cinccino6/electrode
	#2 duskull6/simisage
	#2 hariyama3/clamperl

#4.2
ls -laR duskull6 2>/dev/null | sort -k 1 | grep ^-

	#-r--r--r--  1 s466380 studs 37  9 окт.  16:19 simisage

#4.3
cat -n arcanine4 2>&1 | sort -f | grep -v 'de' 

    #_

#4.4
cat -n cinccino6/electrode | grep 'n$'

    #_

#4.5
ls -ltR lab0 2>&1 | grep '6$' | grep -v 'total' | head -n3

	#lrwxr-xr-x  1 s466380 studs   9 10 окт.  12:54 Copy_28 -> ./cinccino6
	#drwxrwxrwx  4 s466380 studs   5 10 окт.  12:54 duskull6
	#drwxrwxrwx  4 s466380 studs   5 10 окт.  12:54 cinccino6

#4.6
cat -n arcanine4 2>/tmp/hsperfdata_s466380 | grep 'n$'

    #_

#5 

rm arcanine4
chmod u+w duskull6/simisage
rm duskull6/simisage
rm hariyama3/clamperlwatchog*
rm hariyama3/clamperlskit*
chmod -R 777 cinccino6
rm -drf cinccino6
rm -rf cinccino6/sudowoodo

