# testdoc

สวัสดีครับผม


> เทสคำคม

```<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', 'HomeController@countdown');
Route::get('/test', 'TestController@check');
Route::get('/home', 'HomeController@home');
Route::get('/logout', 'Auth\CustomAuthController@getLogout');
Route::get('/dorm/{dorm_id}', 'HomeController@dorm')->where(['dorm_id' => '[0-9]+']);;
Route::get('/search', 'DormController@search');
```
| 0:0 | 1:0 |
| -- | -- |
| 0:2 | 1:2 |
* dedewd
* dwedwedwe
* ddewdwedwe

---
![](IMG_0046.jpg)





