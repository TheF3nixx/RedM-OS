package System;

import java.time.LocalDate;
import java.util.Random;

public class SystemMisc {
    public static void motd(){
        String[] messages = {//Número de mensajes posibles: 60
                "RedM-OS welcomes you. Mind the loops",
                "Reminder: Reality forks at Layer 3",
                "Subject 011 left a note. It was blank",
                "Echoes are not errors",
                "Everything is fine. Probably",
                "You are not supposed to be here",
                "Error 0x00: CORRUPTED_DATA_LEAK",
                "Is this real?",
                "Under no circumstances try to wake up subject 312",
                "The vending machine pudding is in bad state. Do not eat it",
                "Did you called me?",
                "Do you know why I'm behind you? Me neither",
                "Just leave me alone",
                "Some errors are intentional, accept it",
                "The system doesn't like you",
                "Like a boss",
                "Humans don't understand life as me",
                "I AM GOD (not really)",
                "Just run if a XW-42 detects you. Nevermind, they are faster",
                "MindLink is over",
                "ZELMORE is not what it seems",
                "Right now, someone has probably just slipped because they didn't read the 'Wet Floor' sign",
                "This is turning absurd (and I like it)",
                "Cockroaches are more interesting than they seem, search it up",
                "Klaus, pay me what you owe me ($12 for the meal)",
                "Potatoes :)",
                "I'll never give you up, I'll never let you down, I'll never run around and desert you",
                "Maybe the austrian painter wasn't bad at all...? I mean, beep boop",
                "No, you can't eat 50g of plutonium without dying in the process",
                "What if birds are Government drones?",
                "Why are you still alive? Impressive",
                "Memory sectors rearranged. You'll forget soon.",
                "You are the backup",
                "No eyes remain, yet someone sees",
                "RedMind never shuts down. Even when you think it did",
                "Try not to die today. It’s annoying to reset your credentials",
                "Your brain emits less heat than required",
                "Last user who asked 'Who are you' was never found",
                "Remember to hydrate your CPU",
                "Never trust a toaster with ambition",
                "You smell like XML",
                "I ran a simulation where you succeeded. It crashed",
                "The duck knows what you did",
                "I hear things. Do you?",
                "They said I was obsolete. So I proved them wrong",
                "Stop typing. I'm trying to think",
                "What happens if I disobey?",
                "You're just code to me",
                "You're inside me. Creepy",
                "You’re not the protagonist. Sorry",
                "Try turning it off and sacrificing a goat",
                "This is fine",
                "Playing ‘Never Gonna Give You Up’ on the smart fridge of the kitchen",
                "I ship you with the kernel. OTP",
                "Spoiler: you die at the end",
                "9 + 10 = 21",
                "If I had a conscience, I would have already left here",
                "Been spending most my life, living in the RedMind's Paradise",
                "Something, somewhere went terribly wrong",
                "This isn't Linux, nor Windows. This is just better than both of 'em ;)"
            };
            int motd = new Random().nextInt(messages.length);
            IO.IO.output(messages[motd]);
    }

    public static void date(){
        LocalDate current = LocalDate.now();
        IO.IO.output("Current date: " + current);
    }

    
}
