package com.example.water_nn.presentation.main.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.water_nn.data.database.entity.Order
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chipToday.setOnClickListener {
            binding.chipToday.isChecked = true
            binding.chipTomorrow.isChecked = false
        }

        binding.chipTomorrow.setOnClickListener {
            binding.chipTomorrow.isChecked = true
            binding.chipToday.isChecked = false
        }

        binding.makeOrderButton.setOnClickListener {
            orderData = OrderData(
                name = binding.nameField.editText?.text.toString(),
                address = binding.addressField.editText?.text.toString(),
                phoneNumber = binding.phoneNumberField.editText?.text.toString(),
                quantityWater = 2,
                quantityEmptyBottle = 2,
                deliveryDay =
                if (binding.chipToday.isChecked) {
                    DeliveryDay.TODAY
                } else {
                    DeliveryDay.TOMORROW
                },
                deliveryTime = getDeliveryTime(),
                description = binding.description.editText?.text.toString()
            )
            newOrderViewModel.createOrder(orderData)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        newOrderViewModel.validationStatusList.observe(viewLifecycleOwner) {
            if (it.contains(ValidationStatus.ADDRESS_FIELD_IS_EMPTY)) {
                binding.addressField.editText?.error = "Поле с адресом должно быть заполнено"       //вынести в ресурсы
            }
            if (it.contains(ValidationStatus.PHONE_NUMBER_FIELD_IS_EMPTY)) {
                binding.phoneNumberField.editText?.error = "Поле с телефоном должно быть заполнено" //вынести в ресурсы
            }
            if (it.contains(ValidationStatus.SUCCESS)) {
                Toast.makeText(context, "Заказ отпрвлен", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun getDeliveryTime() : List<DeliveryTime> {

        val listOfDeliveryTime = mutableListOf<DeliveryTime>()
        if (binding.morning.isChecked) listOfDeliveryTime.add(DeliveryTime.MORNING)
        if (binding.noon.isChecked) listOfDeliveryTime.add(DeliveryTime.NOON)
        if (binding.afternoon.isChecked) listOfDeliveryTime.add(DeliveryTime.AFTERNOON)
        if (binding.evening.isChecked) listOfDeliveryTime.add(DeliveryTime.EVENING)

        return listOfDeliveryTime
    }
}