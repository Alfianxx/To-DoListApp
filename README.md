To-DoListApp

Project Overview
These are the list of features that is available on the starter project for this app. You will need to have a good understanding of all these features to be able to do the simulation.

List Screen
2021021809055774d095908b3aba4b86bc0ec8c0f939d1.jpeg
Option Menu :
Filter tasks by all, active, or completed.
Navigate to the Settings screen.
Floating Action Button (FAB) :
android.R.drawable.ic_input_add
List item :
Checkbox (R.id.item_checkbox)
TaskTitleView (R.id.item_tv_title)
TextAppearance : MaterialComponents.Headline6
MaxLines  :1
TextView (R.id.item_tv_description)
TextAppearance : MaterialComponents.Subtitle2

Add Task Screen
20210218090557d630c97a00fa188318f0e24ddd95d0fa.jpeg
Option Menu:
Save task
Input :
TextInputEditText (R.id.add_ed_title)
MaxLines : 1
TextInputEditText (R.id.add_ed_description)
MaxLines : 5
ImageButton 
onClick : showDatePicker
src : @drawabale/ic_date

Detail Task Screen
20210218090556363b901894787e3c0db9f5ebc2409ff4.jpeg
Show Data :
TextInputEditText (R.id.detail_ed_title)
MaxLines : 1
Focusable : false
TextInputEditText (R.id.detail_ed_description)
MaxLines : 5
Focusable : false
TextInputEditText (R.id.detail_ed_due_date)
Focusable : false
Button (R.id.btn_delete_task)
backgroundTint: @android:color/holo_red_light
Up Navigation Button :
Navigates to List Screen

Settings Screen
2021021809055740d5d9609dd26b1fdab218fcc2972022.jpeg
Setting Preference:
Reminder Notification
Toggle setting to enable or disable daily reminder notification. 
Up Navigation Button :
Navigates to List Screen

Notification
20210218090557c25b2e1b38a0cded6e237e05f21ee87f.jpeg

Small icon : R.drawable.ic_notification
Content title : Nearest Active Task Title
Content text : R.string.notify_content with Nearest Active Task Due Date

//===============================================================================================

Code Submission
Complete the tasks below. You can download the starter project here:
Starter Project To-Do App

Define a local database table and DAO (data access object) based on schema in app/schemas/tasks.json. Use FilterUtils#geFilteredQuery to create a filterable query.
Initiate RecyclerView with TaskAdapter and update database when onCheckChange.
Display title in list-item based on state using TitleTextView (CustomView).
Show a detailed task when the list is selected and implement a delete action.
Create AddTaskViewModel class to insert a new "task" into the database.
Schedule and cancel daily reminder using WorkManager. If notification preference is on, get the nearest active task from the repository and show notification with pending intent.
Address the following comment from the QA team:
SnackBar does not show when the task completed/activated.
Write UI test to validate when user tap Add Task (+), the AddTaskActivity displayed.


If your submission does not match the required criteria, it will be rejected by our Academy Code Reviewers. Below are some items that you will need to avoid:

1. The initial data list from local JSON does not appear.
2. Filter menu not working properly.
3. The state of the TitleTextView does not match.
4. Failed to add new data.
5. Failed to delete data, either from the detail page or by swiping the list.
6. Data is not updated and SnackBar does not appear when the checkbox is checked.
7. Reminder notifications cannot be displayed on Oreo devices and above.
8. UI testing failed or not according to the scenario.
9. Having a Zip file in the Zip file that you submitted (multiple layers of zip files).
10. Force closed application.
11. The project cannot be built.
12. Submit files other than the Android Studio project.
13. Submitting projects that are not your own work.
14. Delete or code in starter project/base code. (example: change GridLayout to LinearLayout).
