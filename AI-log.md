# AI-log – LittleFarm (CN201 Final Project)

บันทึกว่าทีมใช้ AI ตรงไหน ถามอะไร และทีมแก้หรือไม่เอาอะไร

| วันที่ | ใคร | ใช้ AI ทำอะไร | prompt หลัก (ย่อ) | ทีมแก้ / ไม่เอาอะไร เพราะอะไร |
|---|---|---|---|---|
| 2026-11-13 | Supakjira Songsawang| วิเคราะห์ว่างานยังขาดอะไรตามเกณฑ์ | "จากโปรเจกต์เอาภาพรวม ควรแก้ไขอะไรบ้าง" | ไม่แยก package ตามที่ AI ทดสอบให้ดู เพราะคำสั่ง javac -d out src/*.java ของ brief จะคอมไพล์ไม่ผ่าน |
| 2026-11-13 | Supakjira Songsawwang | ร่างไฟล์แบ่งงาน Excel พร้อมโค้ดตัวอย่าง | "ทำ Excel แบ่งงานเลย" | (แต่ละคนเติมแถวของตัวเองว่าแก้/เข้าใจโค้ดตรงไหน) |
| 2026-11-14 | Panita Chanowit | ใช้ AI ช่วยไล่ขั้นตอน Git และแก้ปัญหา terminal ตอนทำ golden test | "ทำตามไฟล์ ตอนนี้ทำถึง SaveGame.java แล้ว" | เปลี่ยนคำสั่ง cmd /c เป็น cmd //c เพราะใช้ Git Bash; เลือก merge แบบปกติ ไม่ squash เพื่อให้ 2 commit แยกกัน |
| 2026-11-14 | Supakjira Songsawang | แปลง Machine เป็น abstract class พร้อม 3 คลาสลูกและ Factory | "เปลี่ยน Machine เป็น abstract class และทำ polymorphism" | ตรวจสอบให้ signature เมธอดตรงตาม checklist และคงฟังก์ชันวาดส่วนกลางไว้ |
| 2026-11-14 | Pasika Sattraporn | เพิ่ม SaveFileException ใน SaveGame.java และ FarmScreen.java และทดสอบการเซฟ/โหลด Cake Maker | "เพิ่ม SaveFileException และตรวจสอบไฟล์เซฟเสียอย่างไร" | โค้ดตัวอย่างร่างโดย AI แล้วตรวจสอบและทดสอบเอง โดยแก้ไฟล์เซฟหลายกรณีและตรวจสอบ error |
| 2026-11-14 | Benchaphon Pinakasa | ใช้ Ai ช่วยวางแผนงานและร่างโค้ด Strategy ราคาตลาด (PriceStrategy, NormalPrice, FestivalPrice, HardTimesPrice, PriceStrategyFactory) กับส่วนแก้ Main, GamePanel, FarmScreen | "ใช้Aiช่วยเขียนโค้ด Strategy ราคาตามตาราง" และ "แก้ 3 ไฟล์บนโค้ดล่าสุด" | ตรวจ git diff เอง (3 ไฟล์ 18 บรรทัด) และทดสอบ 4 แบบ (festival 90G, hard 42G, ปกติ 60G, คำมั่วขึ้น Unknown word) ผ่านหมด |