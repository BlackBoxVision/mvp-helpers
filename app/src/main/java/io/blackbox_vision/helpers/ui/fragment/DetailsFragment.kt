package io.blackbox_vision.helpers.ui.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.Button
import android.widget.Toast

import butterknife.BindView

import io.blackbox_vision.helpers.R
import io.blackbox_vision.helpers.util.MockUtils
import io.blackbox_vision.helpers.logic.presenter.DetailsPresenter
import io.blackbox_vision.helpers.logic.view.DetailsView
import io.blackbox_vision.mvphelpers.ui.fragment.BaseFragment


class DetailsFragment : BaseFragment<DetailsPresenter>(), DetailsView {

    @BindView(R.id.sample_button)
    internal lateinit var button: Button

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener { v -> presenter!!.findRequiredInformation(MockUtils.SAMPLE) }
    }

    override fun addPresenter(): DetailsPresenter {
        return DetailsPresenter()
    }

    override val layout: Int
        @LayoutRes
        get() = R.layout.fragment_details

    override fun onPresenterCreated(presenter: DetailsPresenter) {
        presenter.attachView(this)
    }

    override fun onInfoReceived(information: Bundle) {
        Toast.makeText(context, "This is the information ---> ${information.get(MockUtils.SAMPLE)!!}", Toast.LENGTH_LONG).show()
    }

    override fun onInfoError(errorMessage: String) {
        Toast.makeText(context, "This is the error ---> $errorMessage", Toast.LENGTH_SHORT).show()
    }
}
