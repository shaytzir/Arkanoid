# 315314930
# Shay Tzirin

compile: bin
	javac -d bin -cp biuoop-1.4.jar src/*/*.java src/*.java
run:
	java -cp biuoop-1.4.jar:bin:resources Ass6Game 
jar:
	jar cfm ass6game.jar Manifest.mf -C bin . -C resources .
bin:
	mkdir bin