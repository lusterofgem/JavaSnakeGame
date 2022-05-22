PROJECT = JavaSnakeGame

.PHONY: all run clean

all: $(PROJECT).jar

run: all
	java -jar $(PROJECT).jar

clean:
	rm -rf class
	rm $(PROJECT).jar

$(PROJECT).jar: MANIFEST.MF $(subst src/,class/,$(subst .java,.class,$(wildcard src/javasnakegame/*.java)))
	jar -vcmf $< $@ $(patsubst %,-C class %, $(filter-out $<,$(subst class/,,$^)))

class/javasnakegame/%.class: src/javasnakegame/%.java
	javac -cp src -d class $^
