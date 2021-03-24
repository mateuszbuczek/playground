import {Component} from '@angular/core';
import {PhotosService} from '../photos.service';

@Component({
  selector: 'app-photo-show',
  templateUrl: './photo-show.component.html',
  styleUrls: ['./photo-show.component.css']
})
export class PhotoShowComponent {

  photoUrl: string;

  constructor(private photosService: PhotosService) {
    this.fetchPhoto();
  }

  onClick() {
    this.fetchPhoto();
  }

  private fetchPhoto() {
    this.photosService.getPhoto().subscribe((res) => {
      this.photoUrl = res.urls.regular;
    });
  }
}
