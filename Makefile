JC = javac
R = java
SUB = GUI/
DRIVER = Driver
JFlags = -g


run:
	$(JC) $(JFlags) $(SUB)*.java $(DRIVER).java  -d classfiles
	$(R) -cp classfiles $(DRIVER)

clean:
	rm classfiles/ -r