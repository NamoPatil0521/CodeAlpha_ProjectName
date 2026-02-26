import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class ChatbotGUI {

    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private HashMap<String, String> knowledgeBase;

    public ChatbotGUI() {
        createKnowledgeBase();
        createGUI();
    }

    // NLP Rule-based Knowledge Base
    private void createKnowledgeBase() {
        knowledgeBase = new HashMap<>();

        knowledgeBase.put("hello", "Hello! How can I help you?");
        knowledgeBase.put("hi", "Hi there! Nice to meet you.");
        knowledgeBase.put("how are you", "I am fine. Thank you!");
        knowledgeBase.put("your name", "My name is Java AI Chatbot.");
        knowledgeBase.put("what is java", "Java is a programming language.");
        knowledgeBase.put("ai", "AI means Artificial Intelligence.");
        knowledgeBase.put("help", "I can answer questions about Java, AI, and general queries.");
        knowledgeBase.put("bye", "Goodbye! Have a nice day.");
    }

    // GUI creation
    private void createGUI() {
        frame = new JFrame("AI Chatbot");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();

        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                respond();
            }
        });

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                respond();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        chatArea.append("Bot: Hello! I am your AI Chatbot.\n");

        frame.setVisible(true);
    }

    // NLP Processing Logic
    private void respond() {

        String userInput = inputField.getText().toLowerCase();
        chatArea.append("You: " + userInput + "\n");

        String response = "Sorry, I don't understand.";

        for (String key : knowledgeBase.keySet()) {
            if (userInput.contains(key)) {
                response = knowledgeBase.get(key);
                break;
            }
        }

        chatArea.append("Bot: " + response + "\n\n");

        inputField.setText("");
    }

    public static void main(String[] args) {
        new ChatbotGUI();
    }
}
