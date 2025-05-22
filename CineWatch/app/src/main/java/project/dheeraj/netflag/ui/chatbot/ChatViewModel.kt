package project.dheeraj.netflag.ui.chatbot

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import project.dheeraj.netflag.utils.CONSTANTS
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = CONSTANTS.API_GEMINI,
    )

    fun sendMessage(question : String){
        viewModelScope.launch {

            try{
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role){ text(it.message) }
                    }.toList()
                )

                messageList.add(MessageModel(question,"user"))
                messageList.add(MessageModel("Typing....","model"))

                val response = chat.sendMessage(question)
                if (messageList.isNotEmpty()) {
                    messageList.removeAt(messageList.size - 1)
                }

                messageList.add(MessageModel(response.text.toString(),"model"))
            }catch (e : Exception){
                if (messageList.isNotEmpty()) {
                    messageList.removeAt(messageList.size - 1)
                }

                messageList.add(MessageModel("Error : "+e.message.toString(),"model"))
            }


        }
    }
}