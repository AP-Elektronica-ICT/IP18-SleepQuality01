package be.eaict.sleepqualitymeter;

import java.util.ArrayList;
import java.util.List;

public class TipRepo {
    float maxHeartbeat, maxTemp, maxHumidity, maxLuminosity, maxNoise;
    int totalsleeptime;
    LogicandCalc calc = new LogicandCalc();
    public List<String> getHeartbeatTips() {
        List<String> heartBeatTips = new ArrayList<>();
        heartBeatTips.add("Your maximum heartbeat was: " + Float.toString(maxHeartbeat) + " this is " + String.format("%.1f", maxHeartbeat - 100) + " too much." + System.getProperty("line.separator") + "Try to use these tips to lower your heartbeat while sleeping" + System.getProperty("line.separator") + System.getProperty("line.separator"));
        heartBeatTips.add("Exercise more." + System.getProperty("line.separator") + "When you take a brisk walk, swim, or bicycle, your heart beats faster during the activity and for a short time afterward. But exercising every day gradually slows the resting heart rate.");
        heartBeatTips.add("Reduce stress." + System.getProperty("line.separator") + "Performing the relaxation response, meditation, tai chi, and other stress-busting techniques lowers the resting heart rate over time.");
        heartBeatTips.add("Avoid tobacco products." + System.getProperty("line.separator") + "Smokers have higher resting heart rates. Quitting brings it back down.");
        heartBeatTips.add("Lose weight if necessary." + System.getProperty("line.separator") + "The larger the body, the more the heart must work to supply it with blood. Losing weight can help slow an elevated heart rate.");
        heartBeatTips.add("Be cool." + System.getProperty("line.separator") + "Make sure your surroundings are cool and comfortable. High temperatures and humidity can increase blood flow and heart rate. ");
        heartBeatTips.add("Medication." + System.getProperty("line.separator") + "If you experience an incredibly high heart rate frequently over time, you can get a prescription for heart rate lowering medications from your doctor. Set up a meeting with your doctor to decide whether medication is the right path for you.");
        heartBeatTips.add("Change your diet." + System.getProperty("line.separator") + "Eat heart-healthy foods which help your body to regulate its heart rate. Try eating more salmon, sardines or mackerel, whole grains, green leafy vegetables, nuts, and foods high in potassium like bananas and avocados");
        heartBeatTips.add("Empty your bladder regularly." + System.getProperty("line.separator") + "People who hold their urine until their bladder is really full will increase their heart rates by as much as 9 bpm. A really full bladder increases sympathetic nervous system activity, which constricts blood vessels and forces your heart to beat faster.");
        return heartBeatTips;
    }
    public List<String> getMovementTips() {
        List<String> movementTips = new ArrayList<>();
        movementTips.add("According to this recording you moved too much." + System.getProperty("line.separator") + "Try to use these tips to improve your sleep" + System.getProperty("line.separator") + System.getProperty("line.separator"));
        movementTips.add("Sleep Schedule." + System.getProperty("line.separator") + "Set a sleep schedule for yourself. Going to be at different hours can confuse your body to when it should be lying still");
        movementTips.add("Relaxtion." + System.getProperty("line.separator") + "If you lie down and don’t fall asleep within approximately 15 to 20 minutes, get up out of bed and do something that relaxes you, such as reading a book");
        movementTips.add("Avoid drinking or eating before bedtime." + System.getProperty("line.separator") + "If you do feel hungry, try to eat a very light meal to satisfy the hunger until morning");
        movementTips.add("Make sure you are relaxed in your bedroom." + System.getProperty("line.separator") + "Some people use a fan, soothing waves or the sound of birds to make sure they feel relaxed");
        movementTips.add("Choose a mattress and pillow you are comfortable to." + System.getProperty("line.separator") + "If you do not feel comfortable on your current mattress, try changing to one that suits your needs");
        movementTips.add("Stop napping." + System.getProperty("line.separator") + "Taking a nap during the day affects the deep sleep you get during the night, avoid taking naps longer than 30 minutes during the day");
        movementTips.add("Exercise more." + System.getProperty("line.separator") + "People who exercise in any manner usually sleep better at night, try taking the stairs more instead of the elevator, stretch before bed, yoga, ride your bike more!");
        movementTips.add("Reduce stress." + System.getProperty("line.separator") + "Movement during your sleep can be an effect of stress. Try to take a warm shower before bed or try anything else that will lower your stress level");
        return movementTips;
    }
    public List<String> getTooMuchSleepTips() {
        List<String> tooMuchSleepTips = new ArrayList<>();
        tooMuchSleepTips.add("You slept: " + calc.SleepLengthString(new SleepLength(totalsleeptime)) + "." + System.getProperty("line.separator") + "The average person sleeps between 7 and 9 hours." + System.getProperty("line.separator") + "This means you slept " + calc.SleepLengthString(new SleepLength(totalsleeptime - (540/2))) + " too much."  + System.getProperty("line.separator") + System.getProperty("line.separator"));
        tooMuchSleepTips.add("Stay on a routine." + System.getProperty("line.separator") + "Your body gets used to doing the same things at the same time. When it comes to sleep, a steady schedule is best. By going to bed and getting up at the same time, your body will become accustomed to sleeping a certain number of hours at night which can help you avoid sleeping too much. Your body will want to wake up");
        tooMuchSleepTips.add("Be mindful of sleep cycles." + System.getProperty("line.separator") + "One sleep cycle is about 90 minutes; therefore, try to plan the amount of sleep you get around your cycles. In fact, if you find yourself awake slightly before your alarm goes off, you should just get up, rather than entering another cycle. Waking up in the beginning or middle of a cycle can make you groggy");
        tooMuchSleepTips.add("Use light to your advantage."+ System.getProperty("line.separator") + "Open up the curtains, if it's light out already, or go outside for a few minutes. If it's not light out, try using a light box with full spectrum light");
        tooMuchSleepTips.add("Get exercise in earlier in the day." + System.getProperty("line.separator") + "Don't exercise in the three hours before going to sleep. If you exercise too close to bedtime, it may stimulate your mind and body, keeping you awake, meaning you will need more sleep");
        tooMuchSleepTips.add("Don't hit the snooze button." + System.getProperty("line.separator") + "It's tempting to get a few extra minutes of sleep in the morning by hitting snooze on the alarm; however, doing so can make you groggy, which means you'll want to sleep even more. Try to get up as soon as the alarm starts to go off");
        tooMuchSleepTips.add("Look forward to something." + System.getProperty("line.separator") + "You can make getting up easier by having something you love every morning. Maybe it's as simple as a cup of tea or a coffee, or maybe you have a bowl of your favorite cereal. Use something you love as a motivation to get up and moving.");
        tooMuchSleepTips.add("Try a little music." + System.getProperty("line.separator") + "Music can help rev up your energy, especially if it's something you like. Instead of sticking to silence, make your smartphone blast out your favorite song when the alarm clock goes off");
        return tooMuchSleepTips;
    }
    public List<String> getMoreSleepTips() {
        List<String> moreSleepTips = new ArrayList<>();
        moreSleepTips.add("You slept: " + calc.SleepLengthString(new SleepLength(totalsleeptime)) + "." + System.getProperty("line.separator") + "The average person sleeps between 7 and 9 hours."+ System.getProperty("line.separator") + "This means you should try to sleep atleast " + calc.SleepLengthString(new SleepLength((420/2) - totalsleeptime)) + " more."  +System.getProperty("line.separator") + System.getProperty("line.separator"));
        moreSleepTips.add("Use a good, high quality mattress." + System.getProperty("line.separator") + "This is one of the most important things to consider. Good beds don't always mean soft, so get one that provides good support to your back and make sure you are comfortable sleeping on it.");
        moreSleepTips.add("Ensure your head is well supported." + System.getProperty("line.separator") + "Be sure to use a pillow that is comfortable and supportive for your sleeping style. Having the right pillow will ensure that you wake up feeling refreshed and pain-free. If you are comfortable, you will be more likely to sleep longer. ");
        moreSleepTips.add("Stop napping."+ System.getProperty("line.separator") + "Taking a nap during the day affects the deep sleep you get during the night, avoid taking naps longer than 30 minutes during the day");
        moreSleepTips.add("Avoid drinking or eating before bedtime." + System.getProperty("line.separator") + "If you do feel hungry, try to eat a very light meal to satisfy the hunger until morning");
        moreSleepTips.add("Reduce stress." + System.getProperty("line.separator") + "Performing the relaxation response, meditation, tai chi, and other stress-busting techniques lowers the resting heart rate over time.");
        moreSleepTips.add("Ensure proper ventilation and temperature." + System.getProperty("line.separator") + "Keep your bedroom properly ventilated so you get plenty of fresh air.");
        moreSleepTips.add("Keep a fan running." + System.getProperty("line.separator") + "In addition to providing extra airflow and controlling room temperature, a fan produces a low, consistent level of background noise. This can help eliminate auditory stimuli that keep you from falling and staying asleep.");
        moreSleepTips.add("Keep your room dark." + System.getProperty("line.separator") + "Try to keep your room dark at all times. Your brain is stimulated by light signal, so keeping a dark room helps you get to sleep faster. You can help with this by installing blackout blinds or curtains");
        moreSleepTips.add("Eliminate pests and disturbances." + System.getProperty("line.separator") + "Check if your room is free from mosquitoes and other pests. Also, if you have pets in your home, make sure they can’t access your bed or enter your room to avoid disturbances while sleeping.");
        moreSleepTips.add("Use scented candles and sprays." + System.getProperty("line.separator") + "There is evidence that it is easier to sleep in a fresh, clean, or nice smelling space. Try spraying your room with a mild room spray to lighten up your mood and the ambience in your bedroom.");
        return moreSleepTips;
    }
    public List<String> getHumidityTips() {
        List<String> humidityTips = new ArrayList<>();
        humidityTips.add("The maximum measured humidity this night was: " + Float.toString(maxHumidity) + "%" + System.getProperty("line.separator") + "This is " + String.format("%.1f", maxHumidity - 60)+ "% Too much." + System.getProperty("line.separator") + "Try to use these tips to improve the humidity in your bedroom" + System.getProperty("line.separator") +System.getProperty("line.separator"));
        humidityTips.add("Invest in a humidity monitor." + System.getProperty("line.separator") + "Pick up a humidity monitor at a department store or buy one online. You can keep this in your home to monitor humidity levels, as this will help you figure out which activities and appliances raise humidity in your home.");
        humidityTips.add("Run a de-humidifier." + System.getProperty("line.separator") + "A de-humidifier is one of the easiest ways to reduce humidity in your home. You can buy a de-humidifier online or pick one up at a local department store.");
        humidityTips.add("Use fans." + System.getProperty("line.separator") + "If you have fans, keeping them on throughout the day can help de-humidify your home. Proper ventilation helps keep the air from becoming stale and humid.");
        humidityTips.add("Purchase moisture-absorbing materials." + System.getProperty("line.separator") + "There are many types of absorbent crystals you can purchase online or at a department store that reduce humidity in the home. Things like rock salt, DriZair, and Damprid can all be used to dehumidify your living space.");
        humidityTips.add("Turn on your air conditioner." + System.getProperty("line.separator") + "If you have an air conditioner, run it. Most modern air conditioners both de-humidify and cool the air.");
        humidityTips.add("Run a space heater." + System.getProperty("line.separator") + "If your home is humid during cooler months, a space heater can actually help. Warming a room can help reduce humidity if the warmth comes from a dry heat source, like a space heater.");
        humidityTips.add("Open your windows when humidity drops outside." + System.getProperty("line.separator") + "Do not keep your windows closed at all times. Purchase a humidity monitor and keep it on in your home. Check humidity levels outdoors online. If you humidity is lower outside, open your windows for a few hours to air out your home.");
        humidityTips.add("Limit indoor plants." + System.getProperty("line.separator") + "While indoor plants can be an attractive accessory, keeping them in your home can raise humidity levels. Keep indoor plants to a minimum as this will bring down humidity levels");
        return humidityTips;
    }
    public List<String> getTempTips() {
        List<String> tempTips = new ArrayList<>();
        tempTips.add("The maximum measured temperature in your room this night was: " + Float.toString(maxTemp) + "°C" + System.getProperty("line.separator") + "This is " + String.format("%.1f", maxTemp - 18.5) + "°C too warm." + System.getProperty("line.separator") + "Try to use these tips to lower the temperature in your bedroom");
        tempTips.add("Close your blinds/curtains." + System.getProperty("line.separator") + "About 30 percent of unwanted heat comes from windows. Close your window coverings to prevent direct sunlight from heating up the room. If you don’t already have blinds or curtains in the room, invest in some, especially if you have any south or west-facing windows.");
        tempTips.add("Turn off all unnecessary heat-producing devices, appliances and lights" + System.getProperty("line.separator") + "Any device that is powered on is currently contributing to the heat in the room. Unplug or turn off everything you’re not using. Computers and televisions in particular generate a lot of heat.");
        tempTips.add("Pick up the clutter." + System.getProperty("line.separator") + "Piles of clothing and other kinds of clutter absorb heat and keep it trapped in the room. The less clutter you have in the room, the more available space there is for the heat to disperse and the faster it will cool down.");
        tempTips.add("Turn on the ceiling fan(s) and adjust the settings." + System.getProperty("line.separator") +"Ceiling fans are very effective because they move the air around the room and create a draft. They also pull air up when circulating it, and since hot air rises, this will help to cool the room down faster.");
        tempTips.add("Place a tray of ice in front of one of the fans." + System.getProperty("line.separator") +"Create a makeshift air conditioner by filling up a shallow pan, tray or mixing bowl with ice and positioning it in front one of the fans. This will get cold, slightly misty air circulating the room very quickly.");
        tempTips.add("Position a box fan in an open window, facing out"+ System.getProperty("line.separator") +"The fan will pull hot air out of the room and transfer it outside. It will also bring in cooler air.");
        tempTips.add("Open the windows at night. Temperatures typically drop at night, even in the summer"+ System.getProperty("line.separator") +"Take advantage of the cooler air by cracking a few windows at night before you go to sleep.");
        return tempTips;
    }
    public List<String> getNoiseTips() {
        List<String> noiseTips = new ArrayList<>();
        noiseTips.add("The maximum measured noise in your room this night was: " + Float.toString(maxNoise) + "dB" + System.getProperty("line.separator") + "This is " +String.format("%.1f", maxNoise - 40) + "dB too much. Try these tips to lower the sound in your bedroom" + System.getProperty("line.separator") + System.getProperty("line.separator"));
        noiseTips.add("Rearrange the furniture in your room." + System.getProperty("line.separator") + "Sometimes moving the furniture around in your current room can significantly reduce noise pollution at night. You want to make sure that there are large, thick objects blocking or separating you from whatever is causing the most noise.");
        noiseTips.add("Soundproof your windows." + System.getProperty("line.separator") + "If the loud noises are coming from the outside, the best way to block out noises at night is to make sure your windows are insulated. If you choose to install new double-paned windows to your home, then this can be an expensive solution.");
        noiseTips.add("Insulate the floor." + System.getProperty("line.separator") + "If the offending noises are coming from below you, a great option for blocking this out is to insulate the floor, making the layer between your floor and their noise thicker. If you rent the apartment or home, this can be done by laying down thick carpets or rugs, or even installing new, thick carpet if your landlord agrees.");
        noiseTips.add("Move the location of your bedroom." + System.getProperty("line.separator") + "Sometimes the noises at night are amplified simply because of where your bedroom is located within the house or apartment. If your bedroom is a room that is on the main road of the street or next to a room where there is a screaming infant, then switching rooms can help block out a lot of noise at night.");
        return noiseTips;
    }
    public List<String> getLuminosityTips() {
        List<String> luminosityTips = new ArrayList<>();
        luminosityTips.add("Cover your windows with privacy film." + System.getProperty("line.separator") + "Several companies make “privacy film,” which is basically removable, cut-to-size plastic film that you can adhere to your windows. While this film alone will not completely block out light, it will reduce the amount of light that comes through your windows.");
        luminosityTips.add("Tape aluminum foil over your windows." + System.getProperty("line.separator") + "Aluminum helps reflect the sunlight coming in through the windows, so it can reduce your utility bills as well as block light. Use painter’s tape to secure the foil to avoid damaging your windows.");
        luminosityTips.add("Purchase blackout curtains with liners." + System.getProperty("line.separator") + "Blackout curtains are usually made from thicker fabrics and have liners that block light. In addition, they can reduce your utility bills because they help insulate your home.");
        luminosityTips.add("Sew blackout curtains yourself." + System.getProperty("line.separator") + "If you’re crafty, you can often sew curtains yourself for much cheaper than purchasing them. Most fabric stores sell “blackout drapery lining” and thermal fabrics that you can use behind any curtain fabric you choose. You can even sew linings into your existing curtains.");
        luminosityTips.add("Buy blackout shades." + System.getProperty("line.separator") +  "Roller shades, roman shades, and other fabric window shades often block more light than curtains alone. They are widely available from home supply stores, departments stores, and online.");
        luminosityTips.add(" Close blinds and drapes over the window covering." + System.getProperty("line.separator") + "Drapes and blinds will help block any light that still comes in through filmed or foiled windows.");
        luminosityTips.add("Block off the bottom of your door." + System.getProperty("line.separator") + "Rolling up a blanket or towel and placing it along the bottom edge of your door can help stop light from seeping in through the crack. You can also purchase or make a “draft snake,” which is a filled tube that covers the crack below the door.");
        luminosityTips.add("Unplug unused electronic devices." + System.getProperty("line.separator") + "Many devices have lights to indicate that they’re plugged in, charging, or powered on. These can emit a surprising amount of light in your room, so unplug them when they’re not in use to shut those lights off.");
        return luminosityTips;
    }
    public List<String> getSleepPatternTips() {
        List<String> sleepPatternTips = new ArrayList<>();
        sleepPatternTips.add("Your sleeping schedule is out of sync." + System.getProperty("line.separator") + "Use these tips to help you stay on the same schedule." + System.getProperty("line.separator") + System.getProperty("line.separator"));
        sleepPatternTips.add("Stick to a Routine." + System.getProperty("line.separator") + "Go to bed at the same time and do the same activities every night before bed. Your body is getting a cue that it's time to fall asleep.");
        sleepPatternTips.add("Make Mornings Bright." + System.getProperty("line.separator") + "Light tells your body's clock when it's time to wake up. You can help this process. In the morning, turn on bright lights, open the shades, or take a walk in the sunshine. That's a very healthy way to reset your clock.");
        sleepPatternTips.add("Keep Nights Dark." + System.getProperty("line.separator") +"At night, dim the lights to cue your body that it's time for sleep. Also switch off your screens. E-readers, cellphones, and other devices give off blue light, which makes your brain too alert for sleep.");
        sleepPatternTips.add("Work Out." + System.getProperty("line.separator") + "Exercise builds muscle and trims fat, and it could improve sleep, too. People who exercise at least 150 minutes a week sleep better at night and feel more alert during the day." + "The timing of your exercise can make a difference. A high-intensity cardio workout late in the day can disrupt sleep. Save your runs and step classes for the morning or afternoon if you find out that an intense workout interferes with your sleep.");
        sleepPatternTips.add("Watch What -- and When -- You Eat" + System.getProperty("line.separator") + "Sleep isn't the only routine that follows the clock. Your liver, pancreas, and other organs have their own clocks that respond to food. A big late-night meal can throw them out of rhythm. When you eat late, your body also stores more fat and you can put on pounds. Get most of your calories early in the day and then have a light supper. That's for your waistline, and your sleep.");
        sleepPatternTips.add("Keep Naps Short" + System.getProperty("line.separator") + "An afternoon nap can give you a burst of energy to get through the rest of your day. But if you snooze too long, your body will see the nap as your main sleep time. Then it becomes harder and harder to sleep during the night.");
        sleepPatternTips.add("Limit Caffeine" + System.getProperty("line.separator") + "The cup of coffee that wakes you up in the morning has the same effect at night. Cut out the colas and coffee entirely, or avoid anything with caffeine for at least 6 hours before bedtime.");
        return sleepPatternTips;
    }
}
