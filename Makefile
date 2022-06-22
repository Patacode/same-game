MV=mvn
JV=java
OCOMP=target/classes
MAIN=g56080.same.AppConsole

default: build

build:
	$(MV) package

exec-c:
	$(JV) -cp $(OCOMP) $(MAIN) 

exec-fx:
	$(MV) javafx:run

doc:
	javadoc @gen-jvdoc --module-path $(JFX_HOME) --add-modules javafx.graphics,javafx.controls -d doc/

clean:
	$(MV) clean
	
