const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_123";
const BOB_UID = "bob_456";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test("Unauthenticated user: cannot read user profile", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).get());
});

test("Unauthenticated user: cannot read or write user progress", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("progress").doc("all_questions").get());
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("progress").doc("all_questions").set({ items: [] }));
});

test("Authenticated user: can read and write their own profile and progress", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).set({
      userId: ALICE_UID,
      displayName: "Alice",
      totalAttempted: 50,
      totalCorrect: 45
    })
  );

  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("progress").doc("all_questions").set({
      items: [{ questionKey: "q1", timesAttempted: 1, timesCorrect: 1 }]
    })
  );

  const doc = await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).get()
  );
  if (!doc.exists) throw new Error("Document should exist");
});

test("Authenticated user: cannot read or write another user's profile or progress", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertFails(
    aliceDb.collection("users").doc(BOB_UID).get()
  );
  await assertFails(
    aliceDb.collection("users").doc(BOB_UID).collection("progress").doc("all_questions").set({
      items: []
    })
  );
});

test("Student ID user: can read and write their own deterministic student profile and progress", async () => {
  const studentUid = "student_a1b2c3d4e5f607182930";
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertSucceeds(
    unauthDb.collection("users").doc(studentUid).set({
      userId: studentUid,
      displayName: "Student 101",
      totalAttempted: 20,
      totalCorrect: 18
    })
  );

  await assertSucceeds(
    unauthDb.collection("users").doc(studentUid).collection("progress").doc("all_questions").set({
      items: [{ questionKey: "q1", timesAttempted: 1, timesCorrect: 1 }]
    })
  );

  const doc = await assertSucceeds(
    unauthDb.collection("users").doc(studentUid).get()
  );
  if (!doc.exists) throw new Error("Student document should exist");
});
