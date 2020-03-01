# popular-movies-app-2

Third udacity project

The app uses Activities, ViewModels & Live Data, Data Binding, Repository, Room, Retrofit, and Dagger 2.
Even if there is no network, the app will display data saved in DB.

It is divised into 4 main packages :
- common : containing BaseActivity, BaseViewModel and some other common files.
- data : containing a local package, a model package, a remote package and a repository package.
- di : for the DI
- features : containing the different features, i.e. home (HomeActivity, HomeViewModel, etc), detail, settings.

The home screen has a settings menu, where you can select between Popular, Top Rated, or your Favorite movies.
When clicking on an image, the detail screen is displayed. It contains detail information about the selected movie.
At the bottom of the detail screen, there are 2 buttons, one related to Trailers, and the other one related to Reviews of the movie.
Clicking on either of those buttons allows you to see Trailers & Reviews of the movie.

**In order to make the app working, you must put your API key in the MovieDatasource file present in data/remote/ package.**
