JFLAGS = -g
JC = javac
JVM = java
MAIN = Driver
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Driver.java \
	GUI/PegSolitaireFrame.java GUI/PegSolitaireGame.java

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	$(JVM) $(MAIN)
	
clean:
	$(RM) *.class