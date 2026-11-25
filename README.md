# Exam Ready – Grade 12 Life Sciences (Android)

An offline Android quiz app starter project for South African Grade 12 Life Sciences learners.

## Home screen branding
- App name: Exam Ready
- Education brand displayed: Dlamuka Junior Education

## Included
- Topic selection
- Multiple-choice quizzes
- Instant marking and explanations
- Quiz score and best score for the current app session
- Offline question bank (no internet permission or account required)
- Original practice questions written to align broadly with Grade 12 Life Sciences themes

## Important content note
This project does **not** reproduce pages, questions, diagrams, or answer keys from the Mind the Gap book or past examination papers. The questions are original practice material. Check the current DBE CAPS/ATP and approved study materials when expanding the question bank. This is a revision aid, not an official DBE product.

## Open and build
1. Install Android Studio on a computer.
2. Extract this ZIP.
3. In Android Studio, choose **Open** and select the `MyGame_Grade12_LifeSciences` folder.
4. Allow Gradle sync to finish. Use JDK 17.
5. Connect an Android phone with USB debugging enabled, or create an Android emulator.
6. Press **Run**.
7. To create an APK, select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

The generated debug APK is normally placed at:
`app/build/outputs/apk/debug/app-debug.apk`

## Project details
- Native Android / Kotlin
- Jetpack Compose UI
- Minimum Android version: Android 6.0 (API 23)
- No network access required
- Application ID: `za.co.mygame.lifesciences`

## Current starter limitations / next development steps
- Scores currently reset when the app process is closed; persistent progress can be added with DataStore or Room.
- The question bank currently contains 15 sample questions. Add reviewed questions for every CAPS topic before classroom or exam use.
- Add diagrams, timed tests, difficulty levels, bookmarks, and a larger question bank in later versions.
- App size depends on included media and build settings. A useful offline study app does not need to be forced to 100 MB; the initial app will be much smaller.
