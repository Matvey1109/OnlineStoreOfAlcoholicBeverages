# Define variables
SOURCE_DIR = src
BUILD_DIR = bin
MAIN_CLASS = Main
JDBC_DRIVER = lib/postgresql-42.7.4.jar  # Path to the PostgreSQL JDBC driver

# Default rule
all: compile run

# Define compilation rule
compile:
	@mkdir -p $(BUILD_DIR)
	javac -d $(BUILD_DIR) -sourcepath $(SOURCE_DIR) -cp $(JDBC_DRIVER) $(SOURCE_DIR)/$(MAIN_CLASS).java

# Define execution rule
run:
	java -cp $(BUILD_DIR):$(JDBC_DRIVER) $(MAIN_CLASS)

# Define clean rule
clean:
	rm -rf $(BUILD_DIR)/*

# Define help rule
help:
	@echo "Available commands:"
	@echo "  make compile - Compile the Java source code"
	@echo "  make run     - Run the Java program"
	@echo "  make clean   - Clean up compiled files"
	@echo "  make all     - Compile and run the Java program"
	@echo "  make help    - Display available commands"

.PHONY: compile run clean all help
