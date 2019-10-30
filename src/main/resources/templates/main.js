fetch('api/book/list')
     .then(response => response.json())
     .then(books => {
         new Vue({
             el: '#books-table',
             data: {
                 books: books
             },
             methods: {
                 addBook: function(title, genre, authorName) {
                   let addPath = '/api/book/add?title='+title+'&genre='+genre+'&authorName='+authorName
                    fetch(addPath, {
                      method: 'POST'
                    }).then(response => {
                      if (response.ok) {
                        this.books.push({
                          title: title,
                          genre: genre,
                          authorName: authorName
                        })
                      }
                 })
                },
                 deleteBook: function(bookId) {
                     fetch('/api/book/'+bookId, {method:"DELETE"}).then(response => {
                     if(response.ok) {
                         this.books = this.books.filter(function(value, index, arr){
                           return value.id != bookId;
                        })

                      } })
                 }
             }
         })
     });