#!/bin/bash

# This script will correctly initialize your Git repository and push it to GitHub.

# Exit immediately if a command exits with a non-zero status.
set -e

# 1. Add all files to the staging area.
echo "Adding all files to Git..."
git add .

# 2. Create the first commit. This creates the master/main branch.
echo "Creating the first commit..."
git commit -m "Initial commit"

# 3. Rename the local branch to 'main' to match GitHub's standard.
echo "Renaming branch to 'main'..."
git branch -M main

# 4. Push the 'main' branch to your GitHub repository.
echo "Pushing to GitHub..."
git push -u origin main

echo "
Repository setup complete! Your code has been pushed to GitHub."
