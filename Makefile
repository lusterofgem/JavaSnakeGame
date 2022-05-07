PROJECT = PixelSnake

.PHONY: all clean

all : $(PROJECT).jar

clean : 
	rm -rf class
	rm $(PROJECT).jar

PixelSnake.jar : MANIFEST.MF $(subst src/,class/,$(subst .java,.class,$(wildcard src/pixelsnake/*.java)))
	jar -vcmf $< $@ -C class $(filter-out $<,$(subst class/,,$^))

class/%.class : src/%.java
	javac -d class $^