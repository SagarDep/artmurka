package com.artmurka.artmurkaapp.android.di.app

import android.app.Activity
import com.artmurka.artmurkaapp.android.ArtmurkaApplication
import com.artmurka.artmurkaapp.android.di.module.ApplicationModule
import com.artmurka.artmurkaapp.android.views.activities.checkout.CheckoutActivity
import com.artmurka.artmurkaapp.android.views.activities.di.CheckoutActivityComponent
import com.artmurka.artmurkaapp.android.views.activities.di.MainActivityComponent
import com.artmurka.artmurkaapp.android.views.activities.di.SelectedGoodActivityComponent
import com.artmurka.artmurkaapp.android.views.activities.main.MainActivity
import com.artmurka.artmurkaapp.android.views.activities.selectedgood.SelectedGoodActivity
import com.artmurka.artmurkaapp.data.model.di.RequestModule
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Singleton
@Component(modules = [
    (ApplicationModule::class),
    (RequestModule::class),
    (AndroidSupportInjectionModule::class),
    (ApplicationComponent.ActivityBindingsModule::class),
    (ApplicationComponent.FragmentBindingsModule::class),
    (ApplicationComponent.ServiceBindingsModule::class)
])
interface ApplicationComponent : AndroidInjector<ArtmurkaApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<ArtmurkaApplication>() {
        abstract fun applicationModule(module: ApplicationModule): Builder
    }

    @Module(subcomponents = [
        (MainActivityComponent::class),
        (SelectedGoodActivityComponent::class),
        (CheckoutActivityComponent::class)
    ])
    interface ActivityBindingsModule {

        @Binds
        @IntoMap
        @ActivityKey(value = MainActivity::class)
        fun mainActivityComponentBuilder(builder: MainActivityComponent.Builder): AndroidInjector.Factory<out Activity>

        @Binds
        @IntoMap
        @ActivityKey(value = SelectedGoodActivity::class)
        fun selectedGoodActivityComponentBuilder(builder: SelectedGoodActivityComponent.Builder): AndroidInjector.Factory<out Activity>

        @Binds
        @IntoMap
        @ActivityKey(value = CheckoutActivity::class)
        fun selectedCheckoutActivityComponentBuilder(builder: CheckoutActivityComponent.Builder): AndroidInjector.Factory<out Activity>


    }
    @Module(subcomponents = [ ])

    interface FragmentBindingsModule {

    }

    @Module(subcomponents = [  ])
    interface ServiceBindingsModule {

    }
}