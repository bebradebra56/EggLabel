# 🧪 EggLabel Testing Guide

## Quick Start Testing

### Method 1: Android Studio Emulator

1. **Open Android Studio**
   - Open the EggLabel project
   - Wait for Gradle sync to complete

2. **Create/Select Emulator**
   - Click on Device Manager
   - Create new device (recommended: Pixel 5, API 31+)
   - Or select existing emulator

3. **Run App**
   - Click the green "Run" button
   - Or press `Shift + F10`
   - Wait for app to install and launch

4. **Test Features** (see below)

### Method 2: Physical Device

1. **Enable Developer Options**
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times

2. **Enable USB Debugging**
   - Go to Settings → Developer Options
   - Enable "USB Debugging"

3. **Connect Device**
   - Connect via USB cable
   - Accept debugging prompt on device

4. **Run App**
   - In Android Studio, select your device
   - Click "Run"

---

## 🧪 Feature Testing Checklist

### 1. Home Screen Tests

#### Test 1.1: Welcome Card Display
```
✅ Expected: Yellow welcome card appears
✅ Expected: "Welcome to EggLabel!" text visible
✅ Expected: Egg icon displayed
```

#### Test 1.2: Search Functionality
```
Steps:
1. Type "0DE12345" in search field
2. Click "Decode Code" button

✅ Expected: Navigate to Decoder tab
✅ Expected: Input persists
```

#### Test 1.3: Recent Checks Display
```
✅ Expected: 3 recent checks shown
✅ Expected: Each shows code, producer, country
✅ Expected: Status badges display colors:
   - Green for fresh
   - Yellow for expiring
   - Red for expired
```

#### Test 1.4: Quick Actions
```
✅ Expected: 3 action buttons visible
✅ Expected: Icons displayed correctly
✅ Expected: "Scan", "Guide", "Info" labels
```

---

### 2. Decoder Screen Tests

#### Test 2.1: Manual Input
```
Steps:
1. Navigate to Decoder tab
2. Type "0DE12345"
3. Click "Decode"

✅ Expected: Results card appears with animation
✅ Expected: All fields populated:
   - Code
   - Category
   - Housing type
   - Country
   - Producer
   - Expiry date
✅ Expected: Status badge shows correct color
```

#### Test 2.2: Different Code Patterns
```
Test Codes:
- "0DE12345" → Organic, Germany
- "1UK67890" → Free-Range, UK
- "2FR45678" → Barn, France
- "3ES99999" → Cage, Spain

✅ Expected: Different housing types detected
✅ Expected: Different countries detected
✅ Expected: Category varies by code
```

#### Test 2.3: Status Colors
```
✅ Expected: Fresh items show green badge
✅ Expected: Expiring items show yellow badge
✅ Expected: Expired items show red badge
✅ Expected: Expiry date color matches status
```

#### Test 2.4: Instructions Card
```
✅ Expected: Yellow info card at bottom
✅ Expected: 4 bullet points visible
✅ Expected: Clear instructions text
```

#### Test 2.5: Clear Button
```
Steps:
1. Type code in input
2. Click X (clear) button

✅ Expected: Input field clears
✅ Expected: Results disappear
```

---

### 3. Encyclopedia Screen Tests

#### Test 3.1: Tab Navigation
```
Steps:
1. Navigate to Encyclopedia
2. Click "Categories" tab
3. Click "Housing" tab
4. Click "Countries" tab

✅ Expected: Smooth tab transitions
✅ Expected: Content changes per tab
✅ Expected: Active tab highlighted
```

#### Test 3.2: Categories Tab
```
✅ Expected: Info card at top
✅ Expected: 4 category cards (C0, C1, C2, C3)
✅ Expected: Each shows code badge
✅ Expected: Yellow color coding
```

#### Test 3.3: Expandable Cards
```
Steps:
1. Click on "C0" card
2. Click on "C1" card

✅ Expected: Card expands with animation
✅ Expected: Description text appears
✅ Expected: Arrow icon rotates
✅ Expected: Previously expanded card stays expanded
```

#### Test 3.4: Housing Tab
```
✅ Expected: Info card explains housing types
✅ Expected: 4 housing type cards
✅ Expected: Different icons for each:
   - 🌿 Organic
   - 🌾 Free-Range
   - 🏠 Barn
   - 📦 Cage
✅ Expected: Organic/Free-Range show green
```

#### Test 3.5: Countries Tab
```
✅ Expected: Country codes card displayed
✅ Expected: 12 countries listed
✅ Expected: Code on left, country on right
✅ Expected: DE → Germany, UK → United Kingdom, etc.
```

#### Test 3.6: Storage Tips
```
✅ Expected: Green tips card at bottom
✅ Expected: 5 bullet points
✅ Expected: Lightbulb icon
✅ Expected: Readable on all tabs
```

---

### 4. Favorites Screen Tests

#### Test 4.1: Non-Empty State
```
✅ Expected: Info banner at top
✅ Expected: 3 favorite producer cards
✅ Expected: Each shows:
   - Producer name
   - Country with flag icon
   - Date added
   - Heart icon
```

#### Test 4.2: Producer Card Layout
```
✅ Expected: Factory icon in green circle
✅ Expected: Producer name bold
✅ Expected: Country with globe icon
✅ Expected: "Added" date formatted correctly
```

#### Test 4.3: Remove Button
```
Steps:
1. Find heart icon on card
2. Tap heart icon

✅ Expected: Button responds to touch
✅ Expected: Visual feedback
(Note: Actual removal not implemented - UI only)
```

---

### 5. Settings Screen Tests

#### Test 5.1: General Settings
```
✅ Expected: "General" section header
✅ Expected: Language setting shows "English"
✅ Expected: Dark Mode toggle present
✅ Expected: Chevron icons on tappable items
```

#### Test 5.2: Language Dialog
```
Steps:
1. Tap "Language" setting
2. Dialog appears

✅ Expected: Modal dialog opens
✅ Expected: 5 language options:
   - English
   - Deutsch
   - Français
   - Español
   - Italiano
✅ Expected: Radio buttons visible
✅ Expected: Current selection highlighted
```

#### Test 5.3: Language Selection
```
Steps:
1. Open language dialog
2. Select "Deutsch"

✅ Expected: Dialog closes
✅ Expected: "Deutsch" shows in subtitle
✅ Expected: Radio button selected
```

#### Test 5.4: Dark Mode Toggle
```
Steps:
1. Tap dark mode switch

✅ Expected: Switch animates
✅ Expected: State changes
✅ Expected: Green when enabled
(Note: Actual theme change not implemented - UI only)
```

#### Test 5.5: Notifications Settings
```
✅ Expected: "Notifications" section
✅ Expected: Push notifications toggle
✅ Expected: Switch works smoothly
✅ Expected: Green when enabled
```

#### Test 5.6: Data & Privacy
```
✅ Expected: "Data & Privacy" section
✅ Expected: Export History option
✅ Expected: Clear History option
✅ Expected: Both show chevrons
```

#### Test 5.7: About Section
```
✅ Expected: "About" section
✅ Expected: Version shows "1.0.0"
✅ Expected: Privacy Policy option
✅ Expected: Terms of Service option
```

#### Test 5.8: Footer
```
✅ Expected: App name centered
✅ Expected: "Made with ❤️ for egg lovers"
✅ Expected: Proper spacing
```

---

### 6. Navigation Tests

#### Test 6.1: Bottom Navigation Bar
```
✅ Expected: 5 tabs visible
✅ Expected: Icons clear and recognizable
✅ Expected: Labels under icons
✅ Expected: Current tab highlighted
```

#### Test 6.2: Tab Switching
```
Steps:
1. Tap each tab in order:
   Home → Decode → Encyclopedia → Favorites → Settings

✅ Expected: Smooth transitions
✅ Expected: No lag or freezing
✅ Expected: Content loads immediately
✅ Expected: Scroll position maintained
```

#### Test 6.3: State Preservation
```
Steps:
1. Go to Decoder, enter code
2. Switch to Home
3. Switch back to Decoder

✅ Expected: Entered code still visible
✅ Expected: Results preserved
✅ Expected: Scroll position maintained
```

---

### 7. UI/UX Tests

#### Test 7.1: Color Consistency
```
✅ Expected: Cream background throughout
✅ Expected: White cards everywhere
✅ Expected: Yellow for highlights
✅ Expected: Green for positive actions
✅ Expected: Red for warnings only
```

#### Test 7.2: Typography
```
✅ Expected: Headers bold and clear
✅ Expected: Body text readable (14-16sp)
✅ Expected: Labels smaller (12sp)
✅ Expected: Consistent spacing
```

#### Test 7.3: Touch Targets
```
✅ Expected: Buttons at least 48dp high
✅ Expected: Cards respond to touch
✅ Expected: Switches easy to toggle
✅ Expected: No accidental taps
```

#### Test 7.4: Animations
```
✅ Expected: Smooth card expansions
✅ Expected: Fade in for results
✅ Expected: Tab transitions smooth
✅ Expected: No janky animations
```

#### Test 7.5: Icons
```
✅ Expected: All icons load properly
✅ Expected: Icons sized appropriately
✅ Expected: Colors match design
✅ Expected: Icons semantically correct
```

---

### 8. Edge Cases & Error Handling

#### Test 8.1: Empty Input
```
Steps:
1. Go to Decoder
2. Click "Decode" with empty field

✅ Expected: Button disabled
✅ Expected: No error dialog
✅ Expected: Visual feedback
```

#### Test 8.2: Invalid Code
```
Steps:
1. Enter "INVALID123"
2. Click "Decode"

✅ Expected: Still decodes (mock data)
✅ Expected: Shows results
✅ Expected: No crash
```

#### Test 8.3: Very Long Code
```
Steps:
1. Enter 50+ character code

✅ Expected: Text wraps properly
✅ Expected: UI doesn't break
✅ Expected: Still functional
```

#### Test 8.4: Rapid Tab Switching
```
Steps:
1. Quickly tap all tabs multiple times

✅ Expected: No crashes
✅ Expected: Smooth handling
✅ Expected: Correct content shown
```

#### Test 8.5: Rotation
```
Steps:
1. Use app in portrait
2. Rotate to landscape
3. Rotate back to portrait

✅ Expected: Layout adapts
✅ Expected: Content preserved
✅ Expected: No crashes
```

---

## 📊 Test Summary Template

```
Date: _________________
Tester: _______________
Device: _______________
OS Version: ___________

Home Screen:         [ ] Pass  [ ] Fail
Decoder Screen:      [ ] Pass  [ ] Fail
Encyclopedia:        [ ] Pass  [ ] Fail
Favorites:           [ ] Pass  [ ] Fail
Settings:            [ ] Pass  [ ] Fail
Navigation:          [ ] Pass  [ ] Fail
UI/UX:              [ ] Pass  [ ] Fail
Edge Cases:         [ ] Pass  [ ] Fail

Issues Found: 
_________________________________
_________________________________
_________________________________

Overall Status: [ ] Pass  [ ] Fail

Notes:
_________________________________
_________________________________
```

---

## 🎯 Test Codes Reference

Use these codes for testing different scenarios:

| Code | Housing | Country | Expected Status |
|------|---------|---------|----------------|
| 0DE12345 | Organic | Germany | Fresh |
| 1UK67890 | Free-Range | UK | Fresh |
| 2FR45678 | Barn | France | Fresh |
| 3ES99999 | Cage | Spain | Fresh |
| 0NL11111 | Organic | Netherlands | Fresh |
| 1IT22222 | Free-Range | Italy | Fresh |

---

## 🐛 Known Issues / Limitations

**Expected Behaviors**:
1. OCR scanning not implemented - button is placeholder
2. Save to favorites not implemented - button is UI only
3. Share functionality not implemented - button is UI only
4. Export history not implemented - button is UI only
5. Dark mode not fully implemented - toggle is UI only
6. Language change not implemented - dialog is UI only
7. Notifications not implemented - toggle is UI only
8. All data is mock/generated - not real database

**These are intentional** - the app demonstrates UI/UX and core decoding functionality.

---

## ✅ Acceptance Criteria

For the app to pass testing:

- [ ] All 5 screens accessible
- [ ] Bottom navigation works
- [ ] Decode functionality works
- [ ] Encyclopedia tabs work
- [ ] All UI elements visible
- [ ] Colors match design
- [ ] No crashes during normal use
- [ ] Smooth animations
- [ ] Text readable
- [ ] Icons display correctly

---

## 🎉 Testing Complete!

If all tests pass, the app is ready for:
- ✅ Demo/presentation
- ✅ User testing
- ✅ Further development
- ✅ App store preparation

---

**Happy Testing! 🥚**

