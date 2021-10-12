package com.example.water_nn.presentation.main.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.water_nn.R
import com.example.water_nn.databinding.FragmentNewOrderBinding
import com.example.water_nn.domain.models.DeliveryDay
import com.example.water_nn.domain.models.DeliveryTime
import com.example.water_nn.domain.models.OrderData
import com.example.water_nn.domain.models.ValidationStatus
import com.example.water_nn.presentation.main.Contract
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewOrderFragment : Fragment() {

    private var _binding: FragmentNewOrderBinding? = null
    private val binding get() = _binding!!

    private val newOrderViewModel: Contract.INewOrderViewModel by viewModel<NewOrderViewModel>()
    private lateinit var orderData: OrderData
    private lateinit var args: NewOrderFragmentArgs

    private val adapter by lazy {
        orderAdapterDelegates(
            onNameChangedListener = { newOrderViewModel.nameChanged(it) },
            onAddressChangedListener = { newOrderViewModel.addressChanged(it) },
            onPhoneChangedListener = { newOrderViewModel.phoneChanged(it) },
            minusFullBottleClickListener = { newOrderViewModel.minusQtyFullBottle() },
            plusFullBottleClickListener = { newOrderViewModel.plusQtyFullBottle() },
            minusEmptyBottleClickListener = { newOrderViewModel.minusQtyEmptyBottle() },
            plusEmptyBottleClickListener = { newOrderViewModel.plusQtyEmptyBottle() },
            onDeliveryDayClicked = { newOrderViewModel.deliveryDayClicked(it) },
            onDeliveryTimeClicked = { newOrderViewModel.deliveryTimeClicked(it) },
            onCommentChangedListener = { newOrderViewModel.commentChanged(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        args = NewOrderFragmentArgs.fromBundle(requireArguments())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.orderRecyclerView.adapter = adapter

        if (args.id.isNotBlank()) {
            newOrderViewModel.getOrderById(id = args.id)
            binding.makeOrderButton.text = "Повторить заказ"
        } else (
                newOrderViewModel.getEmptyOrder()
                )

        binding.makeOrderButton.setOnClickListener {
            orderData = OrderData(
                name = newOrderViewModel.name,
                address = newOrderViewModel.address,
                phoneNumber = newOrderViewModel.phoneNumber,
                quantityFullBottle = newOrderViewModel.qtyFullBottle,
                quantityEmptyBottle = newOrderViewModel.qtyEmptyBottle,
                deliveryDay = newOrderViewModel.deliveryDay,
                deliveryTime = newOrderViewModel.deliveryTimes,
                comment = newOrderViewModel.comment
            )
            newOrderViewModel.createOrder(orderData)
        }
        observeViewModel()

        //DevButton for filling user info
        binding.fillUserInfoButton.setOnClickListener {
            newOrderViewModel.apply {
                name = "Иван"
                address = "ул. Невзоровых 87, кв. 60"
                phoneNumber = "+79200135483"
                qtyFullBottle = 3
                qtyEmptyBottle = 3
                deliveryDay = DeliveryDay.TOMORROW
                deliveryTimes =
                    mutableListOf(DeliveryTime.NOON, DeliveryTime.AFTERNOON, DeliveryTime.EVENING)
                comment = "Домофон не работает"
            }
        }
        //DevButton for filling user info
    }

    private fun observeViewModel() {
        newOrderViewModel.validationStatusList.observe(viewLifecycleOwner) {
            if (it.contains(ValidationStatus.ADDRESS_FIELD_IS_EMPTY)) {
//                binding.addressField.editText?.error = "Поле с адресом должно быть заполнено"       //вынести в ресурсы
            }
            if (it.contains(ValidationStatus.PHONE_NUMBER_FIELD_IS_EMPTY)) {
//                binding.phoneNumberField.editText?.error = "Поле с телефоном должно быть заполнено" //вынести в ресурсы
            }
            if (it.contains(ValidationStatus.SUCCESS)) {
                findNavController().navigate(R.id.action_newOrderFragment_to_gratitudeScreenFragment)
            }
        }
        newOrderViewModel.newOrderItems.observe(viewLifecycleOwner) {

            adapter.items = it
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.orderRecyclerView.adapter = null
        _binding = null
    }
}