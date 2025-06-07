.PHONY: build
build:
	sbt clean compile dist
	unzip -o target/universal/diceof-1.0.0.zip -d diceof-dist
	mv diceof-dist/diceof-1.0.0/* diceof-dist/
	mv diceof-dist/diceof-1.0.0/.* diceof-dist/ 2>/dev/null || true
	rmdir diceof-dist/diceof-1.0.0
	cp Procfile diceof-dist/
	cd diceof-dist && zip -r ../diceof-ebs.zip .
	cd ..
	rm -rf diceof-dist
	