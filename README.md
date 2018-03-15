## NOTE! This code has been developed on a physical device running API 24, and is not tested with the Android emulator.

# Lab 02: Simple RSS reader


## The idea
Create an application that allows the user to read content from any RSS feed. The app will consist of 3 activities: one with the list of items (ListView, for selecting content), one for article content display (for reading content), and User Preferences (for user to specify the preferences).

## Preferences
The user should be able to specify in the preferences the URL to the RSS feed (RSS2.0-based or Atom-based), and, the limiting number of items that should be displayed in the ListView (10, 20, 50, 100), and the frequency at which the app fetches the content (10min, 60min, once a day). The app will fetch the RSS feed and populate the list UP to the limit number. When user clicks on a particular item, a detailed view should be shown, with the content of the article for that item.

## Hints
Make sure that the logic for the fetching of articles is done by the app automatically with the frequency given by the user Preferences. How would you schedule it? How would you prefetch the articles? For testing purposes, add a button to the main activity (the one with the list), to FORCE a fetch upon press of the button. Final app should not have the "fetch" button.

The content of the article should use WebView such that graphics of the content article is rendered correctly, if the content was an URL. If the content was a plain text, a simple text view can be used.

Make sure you use appropriate facilities (a library?) to help you parsing XML content. Should the user specify if the feed is in one format or another, or can the app detect it automatically? Can you use a library that parses RSS2.0 and Atom? What would be the benefit? What would be the limitation?
