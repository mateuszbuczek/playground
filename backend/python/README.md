docker build --target dev . -t python
docker run -it --rm -v ${PWD}:/workspace python sh