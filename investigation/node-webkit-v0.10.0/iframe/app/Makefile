APP = APP.nw

run: build
ifeq ($(shell uname), Linux)
	`which nw` $(APP)
else
	open -n -a node-webkit $(APP)
endif

build:
	@rm -f $(APP)
	zip -r $(APP) node_modules/ js/ css/ lib/ *.js *.html *.json

clean:
	@rm -f $(APP)

.PHONY: clean run build