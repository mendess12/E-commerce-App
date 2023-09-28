package com.yusufmendes.sisterslabgraduationproject.view.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.adapter.ViewPagerAdapter
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentDetailBinding
import com.yusufmendes.sisterslabgraduationproject.model.AddToCardRequest
import com.yusufmendes.sisterslabgraduationproject.util.extensions.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        val cart = args.product
        with(binding) {
            detailScreenCategoryTv.text = cart.category
            detailScreenDescriptionTv.text = cart.description
            detailScreenRateTv.text = cart.rate.toString()
            detailScreenToolbar.toolbarTitle.text = cart.title
            detailScreenPriceTv.text =
                binding.root.context.getString(R.string.price, cart.price.toString())
            if (cart.saleState) {
                detailScreenSalePriceTv.visibility = View.VISIBLE
                detailScreenSalePriceTv.text =
                    binding.root.context.getString(
                        R.string.price,
                        cart.salePrice.toString()
                    )
                detailScreenPriceTv.setBackgroundResource(R.drawable.discount_line)
            }
        }

        binding.detailScreenToolbar.backToolBar.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.detailScreenAddToBagButton.setOnClickListener {
            viewModel.addToBag(AddToCardRequest("b3sa6dj721312ssadas21d", cart.id))
            observeLiveData()
        }

        init()
        setUpTransformer()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
            }
        })
    }

    private fun observeLiveData() {
        viewModel.addBagLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().popBackStack()
            } else {
                view?.showSnackbar("Sepete ekleme başarısız")
            }
        }
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(50))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager2.setPageTransformer(transformer)
    }

    private fun init() {
        viewPager2 = binding.detailScreenViewPager2
        handler = Handler(Looper.myLooper()!!)

        imageList = ArrayList()
        with(imageList) {
            add(args.product.imageOne)
            add(args.product.imageThree)
            add(args.product.imageTwo)
        }

        viewPagerAdapter = ViewPagerAdapter(imageList, viewPager2)

        with(viewPager2) {
            adapter = viewPagerAdapter
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }
}