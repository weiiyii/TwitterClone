# Project 2 - *Twitter Clone*

**TwitterClone** is an android app that allows a user to view his Twitter timeline. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **4** hours spent in total

## User Stories

The following **required** functionality is completed:

- [ ] User can **sign in to Twitter** using OAuth login
- [ ]	User can **view tweets from their home timeline**
  - [ ] User is displayed the username, name, and body for each tweet
  - [ ] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
- [ ] User can refresh tweets timeline by pulling down to refresh

- [ ] User can **compose and post a new tweet**
  - [ ] User can click a “Compose” icon in the Action Bar on the top right
  - [ ] User can then enter a new tweet and post this to twitter
  - [ ] User is taken back to home timeline with **new tweet visible** in timeline
  - [ ] Newly created tweet should be manually inserted into the timeline and not rely on a full refresh
  - [ ] User can **see a counter with total number of characters left for tweet** on compose tweet page

## Video Walkthrough

Here's a walkthrough of implemented user stories:  

![sign in and view twitter timeline](./walkthrus/signIn.gif)

![tweets timestamp](./walkthrus/timestamp.gif) 

![compose tweets](./walkthrus/compose.gif) 

![count tweet character](./walkthrus/charCount.gif) 

## Open-source libraries used

- [Android Async HTTP](https://github.com/codepath/CPAsyncHttpClient) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright [2022] [Weiyi Chen]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
