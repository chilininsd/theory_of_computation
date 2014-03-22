all: subdirs;
	@echo "\t\t********************************************"
	@echo "\t\t**                                        **" 
	@echo "\t\t**   To run type:                         **" 
	@echo "\t\t**                java -jar regex.jar  	 **" 
	@echo "\t\t**                                        **" 
	@echo "\t\t********************************************"
	
subdirs:
	ant -q -f build.xml jar
	cp dist/regex.jar .
	cp dist/regex.jar ./test

clean:
	ant -q -f build.xml clean
	cd src/; find . -name *.class -exec rm {} \;
	rm -r regex.jar test/regex.jar;
