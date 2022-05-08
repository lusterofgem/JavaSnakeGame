PROJECT = PixelSnake
JAVAFX_FLAG = --module-path "javafx-sdk-18.0.1/lib;javafx-sdk-18.0.1/bin" --add-modules javafx.controls

.PHONY: all run clean

all : $(PROJECT).jar

run: all
	java $(JAVAFX_FLAG) -jar $(PROJECT).jar

clean : 
	rm -rf class
	rm $(PROJECT).jar

PixelSnake.jar : MANIFEST.MF $(subst src/,class/,$(subst .java,.class,$(wildcard src/pixelsnake/*.java)))
	jar -vcmf $< $@ -C class $(filter-out $<,$(subst class/,,$^))

class/%.class : src/%.java
	javac $(JAVAFX_FLAG) -d class $^