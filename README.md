<img src="https://github.com/BlackBoxVision/mvp-helpers/blob/master/art/logo.png" width="425px" height="125px">
> Helper classes to build Android Apps through MVP pattern in a faster way

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MVP%20Helpers-brightgreen.svg?style=flat)]() [![](https://jitpack.io/v/BlackBoxVision/mvp-helpers.svg)](https://jitpack.io/#BlackBoxVision/mvp-helpers) [![Build Status](https://travis-ci.org/BlackBoxVision/mvp-helpers.svg?branch=master)](https://travis-ci.org/BlackBoxVision/mvp-helpers)

##Intro

After working about 3 years with Android development, I learn a lot from the different projects I have made, a lot of mistakes, and a lot of lessons learned. About 1 year ago, or more, in Android, MVP become the selected pattern to make Android Apps. MVP makes your apps code more easier to follow, and also easier to reason. 

**This library exposes a minimal API, that should help you to build well architected Android Apps. ¡Check the following steps to get up and running!**

##Installation

Actually I don't have this library in **JCenter/Maven Central**, so if you want to use, follow the instructions. The library is distributed for Java and Kotlin. Looking for Kotlin variant? [Go here](https://github.com/BlackBoxVision/mvp-helpers/tree/kotlin)

**Gradle**

- Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
	repositories {
		...
		maven { 
			url "https://jitpack.io" 
		}
	}
}
```

- Add the dependency:
```gradle
dependencies {
	 compile 'com.github.BlackBoxVision:mvp-helpers:v0.2.0'
}
```

**Maven**

- Add this line to repositories section in pom.xml:
```xml
<repositories>
	<repository>
	   <id>jitpack.io</id>
		 <url>https://jitpack.io</url>
	</repository>
</repositories>
```
- Add the dependency:
```xml
<dependency>
  <groupId>com.github.BlackBoxVision</groupId>
  <artifactId>mvp-helpers</artifactId>
	<version>v0.2.0</version>
</dependency>
```

**SBT**

- Add it in your build.sbt at the end of resolvers:
```sbt
  resolvers += "jitpack" at "https://jitpack.io"
```

- Add the dependency:
```sbt
  libraryDependencies += "com.github.BlackBoxVision" % "mvp-helpers" % "v0.2.0"	
```

##Core Concepts

The concepts behind this library are the following ones: 
- **View** → The **View** is an interface that contains methods related to UI interaction. Those methods should be implemented in your **Activity, Fragment or View**.

- **Interactor** → The **Interactor** is the class that do the hard work, all the blocking operations like **I/O, Networking, Database** Intectations should be done here. 

- **Presenter** → The **presenter** acts as a middle man between the **Interactor** and the **View**.

![](https://github.com/BlackBoxVision/mvp-helpers/blob/master/art/mvp-helpers-architecture.png)

##Usage example

The usage is really simple: 

**1** - Create your **View** interface by extending the [**BaseView**](https://github.com/BlackBoxVision/mvp-helpers/blob/master/library/src/main/java/io/blackbox_vision/mvphelpers/logic/view/BaseView.java). **BaseView** is an empty interface that acts as water mark for the **Presenter**.

```java
public interface DetailsView extends BaseView {

    void onInfoReceived(@NonNull Bundle information);

    void onInfoError(@NonNull String errorMessage);
}
```

**2** - Create an **Interactor** class by extending the [**BaseInteractor**](https://github.com/BlackBoxVision/mvp-helpers/blob/master/library/src/main/java/io/blackbox_vision/mvphelpers/logic/interactor/BaseInteractor.java) class. The **BaseInteractor** provides you a set of helper methods to deal with **background execution and UIThread interaction**. The methods are the following ones: 

- **runOnUiThread** → use it when you need to post data to the main thread.
- **runOnBackground** → use it when you need to make background processing. 
- **runOnBackground** → use it when you need to make a scheduled task. 
- **cancelTask** → use it when you want to cancel a scheduled task. 

```java
//This example uses Java 8 features, I assume the usage of retrolambda
public final class DetailsInteractor extends BaseInteractor {

    public void retrieveDetailsFromService(@NonNull final String id, @NonNull final OnSuccessListener<Bundle> successListener, @NonNull final OnErrorListener<String> errorListener) {
        runOnBackground(() -> {
            //Getting data from somewhere
            final Bundle data = MockUtils.getMockedData(id);

            runOnUiThread(() -> {
                if (data != null) {
                    successListener.onSuccess(data);
                } else {
                    errorListener.onError("Ups, something went wrong");
                } 
            });
        });
    }
}
```

**3** - Create a **Presenter** class by extending the [**BasePresenter**](https://github.com/BlackBoxVision/mvp-helpers/blob/master/library/src/main/java/io/blackbox_vision/mvphelpers/logic/presenter/BasePresenter.java) class. The **BasePresenter** provides you with a set of helper methods to deal with **View** management. The methods are the following ones:

- **isViewAttached** → check if you have set the view to the presenter, returns to you a boolean value that you should handle in your presenter implementation. 
- **attachView** → add the view to the presenter, so you can start to handle the cicle of view - presenter - interactor interaction.
- **detachView** → dereference the view, setting it to null. This method should be called in the onDestroy method in case of use in Activity, and onDestroyView in case of Fragment usage. 
- **getView** → simple getter, to make your access to the view defined more cleaner.
- **onViewAttached** → callback fired when the view is attached to the presenter, it gives you the view so you can start doing something like restoring state, instantiating the interactors.  
- **onViewDetached** → callback fired when the view is detached from the presenter, in this place you can dereference the objects you won't use anymore. 

```java
//I use method references from Java 8 to point the callbacks to interactor, I assume a working project with Retrolambda
public final class DetailsPresenter extends BasePresenter<DetailsView> {
    private DetailsInteractor interactor;
    
    @Override
    protected void onViewAttached(@NonNull DetailsView view) {
        interactor = new DetailsInteractor();
    }

    @Override
    protected void onViewDetached() {
        interactor = null;
    }

    public void findRequiredInformation(@NonNull String id) {
        if (isViewAttached()) {
            interactor.retrieveDetailsFromService(id, this::onSuccess, this::onError);
        }
    }

    private void onSuccess(@NonNull Bundle information) {
        if (isViewAttached()) {
            getView().onInfoReceived(information);
        }
    }

    private void onError(@NonNull String errorMessage) {
        if (isViewAttached()) {
            getView().onInfoError(errorMessage);
        }
    }
}
```

**4** - Create a custom **PresenterFactory** class to provide the presenter instance. You should implement the **PresenterFactory** interface. 

**Now we have to create a Factory, because I have recently implemented a way to not loose presenter when configuration changes. The BaseActivity/BaseFragment use a Loader to provide the Presenter instance, Android Loaders can survive configuration changes, that's why I select them.** 

```java
class DetailsPresenterFactory implements PresenterFactory<DetailsPresenter> {
	
	@Override
	public DetailsPresenter create() {
		return new DetailsPresenter();
	}
}
```

**5** - Attach this cycle with Android specific classes. You can choice an **Activity/Fragment or also a custom view**. In this case I will show you an example with **Fragment** that inherits from [**BaseFragment**](https://github.com/BlackBoxVision/mvp-helpers/blob/master/library/src/main/java/io/blackbox_vision/mvphelpers/ui/fragment/BaseFragment.java)

The **BaseFragment** comes with a resumed lifecycle, and a set of methods to implement. The methods are the following ones:

- **addPresenter** → in this method you have to create you instance of Presenter. 
- **getLayout** → in this method you have pass the id reference to the layout. 
- **getPresenter** → simple getter, to make your access to the presenter more cleaner.
- **onPresenterCreated** → In this method you can start doing something with the presenter. **¡Now the View is attached automatically to the Presenter!**
- **onPresenterDestroyed** → In this method you can do something, like saving app state. 

```java
public final class DetailsFragment extends BaseFragment<DetailsPresenter, DetailsView> implements DetailsView {
    
    @Override
    protected DetailsPresenterFactory createPresenterFactory() {
      	return new DetailsPresenterFactory();
    }
    
    @LayoutRes
    @Override
    protected int getLayout() {
      	return R.layout.fragment_details;
    }
    
    @Override
    protected void onPresenterCreated(@NonNull DetailsPresenter presenter) {
    	//Do something when presenter it's created
	    getPresenter().getInformationFromId("ssdWRGD132");
    }
    
    @Override
    protected void onPresenterDestroyed() {
	    //Do something when presenter is removed, this method is called in onDestroy	
    }
    
    @Override
    void onInfoReceived(@NonNull Bundle information) {
       Toast.makeText(getContext(), information.toString(), Toast.LENGTH_SHORT).show();
    }
    
    @Override
    void onInfoError(@NonNull String errorMessage) {
       Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
} 
```

##Advise about ButterKnife

From version **0.2.0 of this library**, I have decided to remove butterKnife, in order to not force any dev to use butterKnife. 

##Issues 

If you found a bug, or you have an answer, or whatever. Please, open an [issue](https://github.com/BlackBoxVision/mvp-helpers/issues). I will do the best to fix it, or help you. 

##Contributing

Of course, if you see something that you want to upgrade from this library, or a bug that needs to be solved, **PRs are welcome!**

##Release History

* **0.2.0**
  * **CHANGE**: **BasePresenter** has now two new callbacks, to be notified about **view attachment/detachment**. 
  * **CHANGE**: **BaseActivity/BaseFragment** has now two new callbacks to be notified about **presenter creation/destruction**, also, **addPresenter** callback has been replace with **createPresenterFactory**
  * **CHANGE**: Added **PresenterFactory** interface to create custom factories to provide presenter instances
  * **CHANGE**: Added **PresenterLoader**, an Android Loader, that provides the presenter instance and survives configuration changes. 
  * **BUG FIX**: Fixed issue with **BaseInteractor runOnBackground method**, this method was calling **executor.isTerminated** instead of calling **executor.isShutdown**, this produce a RuntimeException, because of troubles with ThreadPool reuse. Also, the methods have been refactored, to use a **ExecutorService** to get more control instead of an **Executor**.

* **0.1.0** 
  * **CHANGE**: Folder refactor under **UI package**
  * **CHANGE**: Modified **BasePresenter** method **registerView** to **attachView** in order to get more consistence
  * **CHANGE**: Added new **runOnBackground** version in **BaseInteractor** that uses a ScheduledExecutorService and also cancel method to stop execution
  * **CHANGE**: Added Custom Views to extend **BaseRelativeLayout, BaseFrameLayout and BaseLinearLayout**
* **0.0.3**
  * **CHANGE**: Removed **ButterKnife** annotation processor	
  * **CHANGE**: Rename **mvphelper** library to library
* **0.0.2**
  * **CHANGE**: Minor updates
* **0.0.1**
  * Work in progress

##License

Distributed under the **MIT license**. See [LICENSE](https://github.com/BlackBoxVision/mvp-helpers/blob/master/LICENSE) for more information.
