package za.co.mygame.lifesciences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF14213D)
private val Teal = Color(0xFF00A896)
private val Pale = Color(0xFFF3F7FA)
private val Ink = Color(0xFF17212B)

data class Question(
    val topic: String,
    val prompt: String,
    val options: List<String>,
    val answer: Int,
    val explanation: String
)

private val topics = listOf(
    "All topics", "DNA, RNA and protein synthesis", "Meiosis",
    "Human reproduction", "Genetics and inheritance",
    "Evolution", "Responding to the environment"
)

private val questionBank = listOf(
    Question("DNA, RNA and protein synthesis",
        "Which DNA base pairs with adenine in a DNA molecule?",
        listOf("Cytosine", "Thymine", "Guanine", "Uracil"), 1,
        "In DNA, adenine pairs with thymine. Cytosine pairs with guanine."),
    Question("DNA, RNA and protein synthesis",
        "What is the main role of messenger RNA (mRNA)?",
        listOf("To carry amino acids to the ribosome", "To carry a copy of genetic information to a ribosome", "To form the cell membrane", "To make energy"), 1,
        "mRNA carries a copied genetic message from DNA to a ribosomes, where proteins are assembled."),
    Question("DNA, RNA and protein synthesis",
        "Where does transcription take place in a human cell?",
        listOf("Ribosome", "Cytoplasm", "Nucleus", "Mitochondrion"), 2,
        "In eukaryotic cells, transcription takes place in the nucleus, where DNA is found."),
    Question("Meiosis",
        "What is the chromosome number in human gametes?",
        listOf("46", "23", "92", "22 pairs"), 1,
        "Human gametes are haploid and contain 23 chromosomes, half the diploid number."),
    Question("Meiosis",
        "During which stage of meiosis does crossing over normally occur?",
        listOf("Prophase I", "Metaphase II", "Anaphase II", "Telophase I"), 0,
        "Crossing over occurs during prophase I when homologous chromosomes pair up."),
    Question("Meiosis",
        "Which process contributes to genetic variation during meiosis?",
        listOf("DNA replication only", "Crossing over and independent assortment", "Mitosis", "Binary fission"), 1,
        "Crossing over and independent assortment produce new combinations of alleles."),
    Question("Human reproduction",
        "Where does fertilisation usually occur in the human female reproductive system?",
        listOf("Uterus", "Vagina", "Oviduct", "Cervix"), 2,
        "Fertilisation usually takes place in the oviduct (fallopian tube)."),
    Question("Human reproduction",
        "Which hormone helps maintain the uterine lining during pregnancy?",
        listOf("Insulin", "Progesterone", "Adrenaline", "Thyroxine"), 1,
        "Progesterone helps maintain the endometrium, supporting pregnancy."),
    Question("Genetics and inheritance",
        "An organism has two identical alleles for a characteristic. What is it called?",
        listOf("Heterozygous", "Homozygous", "Hybrid", "Mutation"), 1,
        "Homozygous means that the two alleles for a gene are identical."),
    Question("Genetics and inheritance",
        "If T is dominant to t, which genotype is heterozygous?",
        listOf("TT", "tt", "Tt", "None of these"), 2,
        "Tt contains two different alleles and is therefore heterozygous."),
    Question("Evolution",
        "Which statement best describes natural selection?",
        listOf("All organisms choose to change", "Individuals with advantageous inherited traits tend to leave more offspring", "Acquired traits are always inherited", "Every individual has the same chance of survival"), 1,
        "Natural selection changes populations over generations because inherited variations affect reproductive success."),
    Question("Evolution",
        "What is a fossil?",
        listOf("A preserved remain or trace of an organism from the past", "A living species", "A type of chromosome", "A newly formed rock only"), 0,
        "Fossils are preserved remains, impressions or traces of organisms from the past."),
    Question("Responding to the environment",
        "Which part of a neuron usually receives impulses from other neurons?",
        listOf("Axon terminal", "Dendrites", "Myelin sheath", "Synaptic vesicle"), 1,
        "Dendrites receive signals and conduct impulses towards the cell body."),
    Question("Responding to the environment",
        "Which division of the nervous system controls many involuntary actions?",
        listOf("Somatic nervous system", "Autonomic nervous system", "Skeletal system", "Central nervous system only"), 1,
        "The autonomic nervous system regulates involuntary activities such as heart rate."),
    Question("Responding to the environment",
        "What is the function of the myelin sheath?",
        listOf("To slow down impulses", "To increase the speed of nerve impulse transmission", "To produce hormones", "To digest neurotransmitters"), 1,
        "The myelin sheath insulates the axon and allows impulses to travel faster.")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MyGameApp() }
    }
}

@Composable
fun MyGameApp() {
    var selectedTopic by remember { mutableStateOf("All topics") }
    var screen by remember { mutableStateOf("home") }
    var quizQuestions by remember { mutableStateOf(emptyList<Question>()) }
    var questionIndex by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableIntStateOf(-1) }
    var score by remember { mutableIntStateOf(0) }
    var answered by remember { mutableStateOf(false) }
    var bestScore by remember { mutableIntStateOf(0) }

    val filtered = if (selectedTopic == "All topics") questionBank else questionBank.filter { it.topic == selectedTopic }

    MaterialTheme(
        colorScheme = lightColorScheme(primary = Teal, secondary = Navy, background = Pale, surface = Color.White)
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = Pale) {
            when (screen) {
                "quiz" -> {
                    val q = quizQuestions[questionIndex]
                    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
                        Text("EXAM READY  /  QUIZ", color = Teal, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(Modifier.height(16.dp))
                        Text("Question ${questionIndex + 1} of ${quizQuestions.size}", color = Navy, fontWeight = FontWeight.SemiBold)
                        LinearProgressIndicator(
                            progress = { (questionIndex + 1).toFloat() / quizQuestions.size },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                            color = Teal
                        )
                        Text(q.topic, color = Teal, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(12.dp))
                        Text(q.prompt, color = Ink, fontSize = 23.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp)
                        Spacer(Modifier.height(20.dp))
                        q.options.forEachIndexed { index, option ->
                            val bg = when {
                                answered && index == q.answer -> Color(0xFFDDF5E8)
                                answered && index == selectedAnswer && index != q.answer -> Color(0xFFFFE4E1)
                                selectedAnswer == index -> Color(0xFFDCEBFF)
                                else -> Color.White
                            }
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
                                    .clickable(enabled = !answered) { selectedAnswer = index },
                                colors = CardDefaults.cardColors(containerColor = bg),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text("${'A' + index}.", color = Teal, fontWeight = FontWeight.Bold)
                                    Spacer(Modifier.width(12.dp))
                                    Text(option, color = Ink, fontSize = 16.sp)
                                }
                            }
                        }
                        if (answered) {
                            Spacer(Modifier.height(12.dp))
                            Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                                Column(Modifier.padding(16.dp)) {
                                    Text(if (selectedAnswer == q.answer) "Correct!" else "Not quite — learn from it",
                                        color = if (selectedAnswer == q.answer) Color(0xFF16834A) else Color(0xFFB45309),
                                        fontWeight = FontWeight.Bold)
                                    Spacer(Modifier.height(6.dp))
                                    Text(q.explanation, color = Ink)
                                }
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                        Button(
                            onClick = {
                                if (!answered) {
                                    if (selectedAnswer == q.answer) score++
                                    answered = true
                                } else if (questionIndex < quizQuestions.lastIndex) {
                                    questionIndex++
                                    selectedAnswer = -1
                                    answered = false
                                } else {
                                    bestScore = maxOf(bestScore, score)
                                    screen = "result"
                                }
                            },
                            enabled = answered || selectedAnswer >= 0,
                            modifier = Modifier.fillMaxWidth().height(54.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Navy)
                        ) {
                            Text(if (!answered) "Check answer" else if (questionIndex == quizQuestions.lastIndex) "See my results" else "Next question")
                        }
                        TextButton(onClick = { screen = "home" }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Text("Exit quiz")
                        }
                    }
                }
                "result" -> {
                    Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Text("QUIZ COMPLETE", color = Teal, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(14.dp))
                        Text("Well done!", color = Navy, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        Text("$score / ${quizQuestions.size}", color = Teal, fontSize = 48.sp, fontWeight = FontWeight.ExtraBold)
                        Text("Your score", color = Ink, fontSize = 17.sp)
                        Spacer(Modifier.height(12.dp))
                        Text("Best score this session: $bestScore", color = Ink)
                        Spacer(Modifier.height(28.dp))
                        Button(onClick = { screen = "home" }, modifier = Modifier.fillMaxWidth()) { Text("Back to home") }
                        OutlinedButton(onClick = {
                            quizQuestions = filtered.shuffled().take(minOf(10, filtered.size))
                            questionIndex = 0; selectedAnswer = -1; score = 0; answered = false; screen = "quiz"
                        }, modifier = Modifier.fillMaxWidth()) { Text("Try again") }
                    }
                }
                else -> {
                    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
                        Text("DLAMUKA JUNIOR EDUCATION", color = Teal, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                        Spacer(Modifier.height(8.dp))
                        Text("Exam Ready", color = Navy, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                        Text("Grade 12 Life Sciences", color = Navy, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                        Text("Learn it. Practise it. Master it.", color = Ink, fontSize = 15.sp)
                        Spacer(Modifier.height(22.dp))
                        Card(colors = CardDefaults.cardColors(containerColor = Navy), shape = RoundedCornerShape(20.dp)) {
                            Column(Modifier.padding(20.dp)) {
                                Text("YOUR STUDY SPACE", color = Color(0xFF9BE7D8), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Spacer(Modifier.height(8.dp))
                                Text("Small steps lead to big results.", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                                Text("Choose a topic and test your knowledge.", color = Color.White.copy(alpha = .85f))
                            }
                        }
                        Spacer(Modifier.height(22.dp))
                        Text("Choose a topic", color = Navy, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        topics.forEach { topic ->
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { selectedTopic = topic },
                                colors = CardDefaults.cardColors(containerColor = if (selectedTopic == topic) Color(0xFFDDF5F0) else Color.White),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text(if (selectedTopic == topic) "●" else "○", color = Teal)
                                    Spacer(Modifier.width(10.dp))
                                    Text(topic, color = Ink, fontWeight = if (selectedTopic == topic) FontWeight.Bold else FontWeight.Normal)
                                }
                            }
                        }
                        Spacer(Modifier.height(18.dp))
                        Text("${filtered.size} practice questions available", color = Ink)
                        Button(
                            onClick = {
                                quizQuestions = filtered.shuffled().take(minOf(10, filtered.size))
                                questionIndex = 0; selectedAnswer = -1; score = 0; answered = false; screen = "quiz"
                            },
                            enabled = filtered.isNotEmpty(),
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Teal)
                        ) { Text("START QUIZ  →", fontWeight = FontWeight.Bold) }
                        Spacer(Modifier.height(16.dp))
                        Text("CAPS-focused revision • Offline • Multiple choice", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
