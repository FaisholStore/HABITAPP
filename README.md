# HabitApp

![Gambar Mockup](D:\projectt\HabitApp\mokcup.jpg)

## Deskripsi

HabitApp adalah aplikasi yang dirancang untuk membantu Anda mengelola kebiasaan sehari-hari. Dengan antarmuka yang intuitif dan fitur-fitur canggih, HabitApp membantu Anda mencapai tujuan kehidupan sehat dan produktif.

## Simulasi

Saya telah melakukan beberapa simulasi yang paling penting, termasuk simulasi AAD (Authentication and Authorization) dan menyertakan link belajar berikut [Dicoding Academy](https://www.dicoding.com/academies/287/).

## Fitur Utama

- **Database Lokal:**
    - Define a local database table and DAO (data access object) based on schema in `app/schemas/habits.json`.
    - Show initial data from JSON.

- **Sorting:**
    - Use `SortUtils.getSortedQuery` to create a sortable query.

- **RecyclerView:**
    - Initiate RecyclerView with HabitAdapter.
    - Delete the habit when the list is swiped.

- **Count Down Timer:**
    - Show count down timer.
    - Notify using WorkManager when the time is up.
    - Open `DetailHabitActivity` when notification is tapped.

- **Dark Mode:**
    - Update the dark mode theme based on the value in ListPreference.

- **Pager Item Layout:**
    - Create a layout for `pager_item` and bind data to the view (See: Random Habit Screen specifications).

## QA Comments Addressed

- All menus do not show.
- Undo in SnackBar not working when the list is deleted using swipe.

## UI Test

Write a UI test to validate that when the user taps "Add Habit (+)," the `AddHabitActivity` is displayed.

## Getting Started

To get started with HabitApp, follow these steps:

