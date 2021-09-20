package com.project.firebasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.project.firebasedemo.databinding.ActivityMainBinding
import com.project.firebasedemo.model.Information

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Logged Out !", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, StartActivity::class.java))
        }

        /**
         * Ces instructions veulent dire qu'on a une base de donnees qui a une branche nommee
         * <ProgrammingKnowledge>, qui elle meme possede une branche <Android>, et cette derniere
         * a une valeur <abcd>
         */
        //Firebase.database.reference
            //.child("ProgrammingKnowledge")
            //.child("Android")
            //.setValue("abcd")
        /**
         * Une map est une collections d'objects cles-valeurs
         */
        /* val map = hashMapOf<String, Any?>(
            "Name" to "Edghi",
            "Email" to "edghi@firebase.com"
        )
        Firebase.database.reference
            .child("ProgrammingKnowledge")
            .child("MultipleValues").updateChildren(map) */

        binding.add.setOnClickListener {
            val name = binding.edit.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this, "No name entered!", Toast.LENGTH_SHORT).show()
            } else {
                /**
                 * On pousse les noms ajoutes dans la branche <ProgrammingKnowledge> sous forme de branche
                 * <Name> avec la valeur du nom
                 */
                /* Firebase.database.reference
                    .child("ProgrammingKnowledge")
                    .push().child("Name").setValue(name) */
                /**
                 * Ici les donnees entrees depuis l'input sont directement ajoutees dans la liste
                 */
                Firebase.database.reference
                    .child("Languages")
                    .child("Name").setValue(name)
            }
        }
        var list = ArrayList<String>()
        val adapter = ArrayAdapter<String>(this, R.layout.list_item, list)
        binding.listView.adapter = adapter

        /**
         * Reference a la branche que l'on veut utiliser/manipuler
         */
        val reference = Firebase.database.reference
            .child("Information")

        /**
         * On met un ecouteur sur la branche de reference pour verifier le changement de donnees dans la
         * base de donnees
         */
        reference.addValueEventListener(object : ValueEventListener {
            /**
             * <snapshot> ici represente les donnees se situant a l'interieur de la branche de reference
             */
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                /**
                 * Dans cette boucle, on recupere la valeur de chaque donnees que l'on met dans <info>
                 * sous forme de classe <Information>, on la transforme puis on la stocke dans la liste
                 */
                for (data: DataSnapshot in snapshot.children) {
                    Log.d("TEST", "Compilation it's here")
                    val info = data.getValue<Information>()
                    val txt = "${info?.getName()} : ${info?.getEmail()}"
                    list.add(txt)
                }
                /**
                 * On notifie a l'adaptateur que les donnees ont changes, pour qu'il fasse la mise a jour
                 */
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}